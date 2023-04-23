package ru.hunt.Request.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.hunt.Request.model.HuntingOrder;
import ru.hunt.Request.model.HuntingOrderType;
import ru.hunt.Request.model.Person;
import ru.hunt.Request.model.Status;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.config.name=application-test")
@Transactional
public class HuntingOrderRepositoryTest {

    @Autowired
    private HuntingOrderRepository huntingOrderRepository;

    @Autowired
    private StatusRepository statusRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testGetOrderByStatus() {
        Status unreviewed = new Status();
        unreviewed.setName("на рассмотрении");
        statusRepository.save(unreviewed);

        for (int i = 0; i < 3; i++) {
            Person p = new Person();
            HuntingOrderType huntingOrderType = new HuntingOrderType();
            HuntingOrder ho = new HuntingOrder();
            ho.setHuntingOrderType(huntingOrderType);
            ho.setPerson(p);
            ho.setStatus(unreviewed);
            huntingOrderRepository.save(ho);
        }

        List<HuntingOrder> orders = huntingOrderRepository.getOrderByStatus(unreviewed);

        assertThat(orders.size()).isEqualTo(3);
    }

    @Test
    public void testChangeOrderStatus() {
        Status unreviewed = new Status();
        unreviewed.setName("на рассмотрении");
        statusRepository.save(unreviewed);
        Status approved = new Status();
        approved.setName("одобрено");
        statusRepository.save(approved);

        Person p = new Person();
        HuntingOrderType huntingOrderType = new HuntingOrderType();
        HuntingOrder ho = new HuntingOrder();
        ho.setId(1L);
        ho.setHuntingOrderType(huntingOrderType);
        ho.setPerson(p);
        ho.setStatus(unreviewed);
        huntingOrderRepository.save(ho);

        huntingOrderRepository.changeOrderStatus(approved, ho.getId());
        entityManager.clear();

        HuntingOrder updatedOrder = huntingOrderRepository.findById(ho.getId()).get();

        assertThat(updatedOrder.getStatus().getName()).isEqualTo(approved.getName());
    }
}