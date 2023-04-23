package ru.hunt.Request.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.hunt.Request.model.HuntingOrderType;
import ru.hunt.Request.model.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.config.name=application-test")
public class ResourceRepositoryTest {

    @Autowired
    private ResourceRepository resourceRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Test
    public void changeAmount_ShouldChangeAmount() {
        var changedAmount = 5;
        var resource = new Resource();
        var id = 0L;
        var amount = 10;
        resource.setAmount(amount);
        resource.setId(id);
        HuntingOrderType huntingOrderType = new HuntingOrderType();
        resource.setHuntingOrderType(huntingOrderType);
        resourceRepository.save(resource);

        resourceRepository.changeAmount(changedAmount, resource.getId());
        entityManager.clear();
        var resourceList = resourceRepository.findAll();
        var updatedResource = resourceList.get(0);

        assertThat(updatedResource).isNotNull();
        assertThat(updatedResource.getAmount()).isEqualTo(changedAmount);
    }
}