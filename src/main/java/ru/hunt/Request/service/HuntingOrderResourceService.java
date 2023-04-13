package ru.hunt.Request.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hunt.Request.model.HuntingOrder;
import ru.hunt.Request.model.HuntingOrderResource;
import ru.hunt.Request.model.Status;
import ru.hunt.Request.repository.HuntingOrderResourceRepository;

import java.util.List;

@Service
public class HuntingOrderResourceService {

    @Autowired
    private HuntingOrderResourceRepository huntingOrderResourceRepository;

    public boolean checkOrderType(List<HuntingOrderResource> resources, String type) {
        for (HuntingOrderResource resource : resources) {
            if (!resource.getResource().getHuntingOrderType().equals(type)) {
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
    public void changeResourceStatus(HuntingOrder huntingOrder, Status status) {
        huntingOrderResourceRepository.changeResourceStatus(status, huntingOrder);
    }
}