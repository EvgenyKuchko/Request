package ru.hunt.Request.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hunt.Request.model.HuntingOrder;
import ru.hunt.Request.model.HuntingOrderResource;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class RequestService {
    @Autowired
    private HuntingOrderService huntingOrderService;

    @Autowired
    private HuntingOrderResourceService huntingOrderResourceService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private ResourceService resourceService;

    private static final String UNREVIEWED_STATUS = "на рассмотрении";
    private static final String NOT_APPROVED_STATUS = "не одобрено";
    private static final String APPROVED_STATUS = "одобрено";
    private static final String PARTIALLY_APPROVED_STATUS = "частично одобрено";

    @Transactional
    public void checkRequests() {
        var orders = huntingOrderService.getUnreviewedSortedOrders(statusService.getStatusByName(UNREVIEWED_STATUS));
        for (HuntingOrder order : orders) {
            var huntingOrderResources = huntingOrderResourceService.getResourcesByOrder(order);
            var horStartSize = huntingOrderResources.size();
            if (!checkType(huntingOrderResources, order)) {
                continue;
            }
            checkSameResource(huntingOrderResources);
            checkOrderByDate(order.getDate(), huntingOrderResources);
            checkDistrict(order, huntingOrderResources);
            checkResourceQuota(huntingOrderResources, order);
            finalCheck(order, huntingOrderResources, horStartSize);
        }
    }

    private boolean checkType(List<HuntingOrderResource> hor, HuntingOrder order) {
        var type = order.getHuntingOrderType().getType();
        if (!huntingOrderResourceService.checkOrderType(hor, type)) {
            huntingOrderService.changeOrderStatus(order, statusService.getStatusByName(NOT_APPROVED_STATUS));
            huntingOrderResourceService.changeResourcesStatusByOrder(order, statusService.getStatusByName(NOT_APPROVED_STATUS));
            return false;
        }
        return true;
    }

    private void checkOrderByDate(LocalDate date, List<HuntingOrderResource> orderResources) {
        for (HuntingOrderResource or : orderResources) {
            if (!huntingOrderResourceService.checkDate(date, or)) {
                huntingOrderResourceService.changeResourceStatus(or, statusService.getStatusByName(NOT_APPROVED_STATUS));
            }
        }
        orderResources.removeIf(hor -> hor.getStatus().equals(statusService.getStatusByName(NOT_APPROVED_STATUS)));
    }

    private void checkDistrict(HuntingOrder ho, List<HuntingOrderResource> hor) {
        if (!hor.isEmpty()) {
            var person = ho.getPerson();
            var acceptedOrderResources = huntingOrderResourceService.getAllAcceptedResourcesByPerson(person, statusService.getStatusByName(APPROVED_STATUS));
            if (!acceptedOrderResources.isEmpty()) {
                Iterator<HuntingOrderResource> iterator = hor.iterator();
                while (iterator.hasNext()) {
                    HuntingOrderResource res = iterator.next();
                    for (HuntingOrderResource acceptedRes : acceptedOrderResources) {
                        if (res.getResource().getName().equals(acceptedRes.getResource().getName())) {
                            huntingOrderResourceService.changeResourceStatus(res, statusService.getStatusByName(NOT_APPROVED_STATUS));
                            iterator.remove();
                            break;
                        }
                    }
                }
            }
        }
    }

    private void checkResourceQuota(List<HuntingOrderResource> hor, HuntingOrder ho) {
        List<HuntingOrderResource> copy = new CopyOnWriteArrayList<>(hor);
        if (!copy.isEmpty()) {
            for (HuntingOrderResource orderResource : copy) {
                var resource = orderResource.getResource();
                if (resource.getAmount() < orderResource.getAmount()) {
                    huntingOrderResourceService.changeResourcesStatusByOrder(ho, statusService.getStatusByName(NOT_APPROVED_STATUS));
                    hor.remove(orderResource);
                } else {
                    huntingOrderResourceService.changeResourcesStatusByOrder(ho, statusService.getStatusByName(APPROVED_STATUS));
                    resourceService.changeAmount(resource, orderResource.getAmount());
                }
            }
        }
    }

    private void checkSameResource(List<HuntingOrderResource> hor) {
        if (hor.size() > 1) {
            List<HuntingOrderResource> dis = hor.stream()
                    .distinct()
                    .collect(Collectors.toList());

            for(HuntingOrderResource res : dis) {
                Iterator<HuntingOrderResource> iterator = hor.iterator();
                while (iterator.hasNext()) {
                    HuntingOrderResource orderResource = iterator.next();
                    if(orderResource.getId() != res.getId() &&
                            orderResource.getResource().getName().equals(res.getResource().getName())) {
                        huntingOrderResourceService.changeResourceStatus(orderResource, statusService.getStatusByName(NOT_APPROVED_STATUS));
                        iterator.remove();
                    }
                }
            }
        }
    }

    private void finalCheck(HuntingOrder ho, List<HuntingOrderResource> hor, int horStartSize) {
        if (hor.size() == horStartSize) {
            huntingOrderService.changeOrderStatus(ho, statusService.getStatusByName(APPROVED_STATUS));
        } else if (horStartSize > hor.size() && hor.size() > 0) {
            huntingOrderService.changeOrderStatus(ho, statusService.getStatusByName(PARTIALLY_APPROVED_STATUS));
        } else if (hor.size() == 0) {
            huntingOrderService.changeOrderStatus(ho, statusService.getStatusByName(NOT_APPROVED_STATUS));
        }
    }
}