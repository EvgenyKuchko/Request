package ru.hunt.Request.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hunt.Request.model.*;
import ru.hunt.Request.repository.HuntingOrderResourceRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class HuntingOrderResourceService {

    @Autowired
    private HuntingOrderResourceRepository huntingOrderResourceRepository;

    public boolean checkOrderType(List<HuntingOrderResource> resources, String type) {
        for (HuntingOrderResource resource : resources) {
            if (!resource.getResource().getHuntingOrderType().getType().equals(type)) {
                return false;
            }
        }
        return true;
    }

    @Transactional
    public List<HuntingOrderResource> getResourcesByOrder(HuntingOrder huntingOrder) {
        return huntingOrderResourceRepository.getResourcesByOrder(huntingOrder);
    }

    @Transactional
    public void changeResourcesStatusByOrder(HuntingOrder huntingOrder, Status status) {
        huntingOrderResourceRepository.changeResourceStatusByOrder(status, huntingOrder);
    }

    public boolean checkDate(LocalDate orderDate, HuntingOrderResource orderResource) {
        Resource r = orderResource.getResource();
        return orderDate.isAfter(r.getStart()) && orderDate.isBefore(r.getFinish());
    }

    @Transactional
    public void changeResourceStatus(HuntingOrderResource huntingOrderResource, Status status) {
        huntingOrderResourceRepository.changeResourceStatus(status, huntingOrderResource.getId());
    }

    @Transactional
    public List<HuntingOrderResource> getAllAcceptedResourcesByPerson(Person person, Status status) {
        return huntingOrderResourceRepository.getAllAcceptedResourcesByPerson(person, status);
    }
}