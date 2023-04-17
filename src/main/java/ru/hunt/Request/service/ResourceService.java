package ru.hunt.Request.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hunt.Request.model.Resource;
import ru.hunt.Request.repository.ResourceRepository;

@Service
public class ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;

    @Transactional
    public void changeAmount(Resource resource, int amount) {
        resourceRepository.changeAmount(amount, resource.getId());
    }
}