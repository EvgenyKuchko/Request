package ru.hunt.Request.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hunt.Request.model.HuntingOrder;
import ru.hunt.Request.model.Status;

import java.time.LocalDate;

@Service
public class RequestService {
    @Autowired
    private HuntingOrderService huntingOrderService;

    @Autowired
    private HuntingOrderResourceService huntingOrderResourceService;

    @Autowired
    private StatusService statusService;

    @Transactional
    public void checkRequests() {
        var orders = huntingOrderService.getUnreviewedSortedOrders(statusService.getStatusByName("на рассмотрении"));
        for (HuntingOrder order : orders) {
            var type = order.getHuntingOrderType().getType();
            var huntingOrderResources = huntingOrderResourceService.getResourcesByOrder(order);
            if (!huntingOrderResourceService.checkOrderType(huntingOrderResources, type)) {
                Status status = statusService.getStatusByName("не одобрено");
                huntingOrderService.declineTheOrder(order, status);
                huntingOrderResourceService.changeResourceStatus(order, status);
            }
            LocalDate orderDate = order.getDate();

        }
    }
}