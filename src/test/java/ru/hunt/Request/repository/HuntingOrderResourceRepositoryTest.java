package ru.hunt.Request.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.hunt.Request.model.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.config.name=application-test")
@Transactional
public class HuntingOrderResourceRepositoryTest {

    @Autowired
    private HuntingOrderResourceRepository huntingOrderResourceRepository;

    @Autowired
    private HuntingOrderRepository huntingOrderRepository;

    @Autowired
    private StatusRepository statusRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private static HuntingOrder huntingOrder;
    private static List<HuntingOrderResource> huntingOrderResources;
    private static Status approved;
    private static Status unreviewed;

    @Before
    public void setUp() {
        approved = new Status();
        approved.setName("одобрено");
        statusRepository.save(approved);
        huntingOrder = new HuntingOrder();
        huntingOrder.setId(1L);
        Person person = new Person();
        huntingOrder.setPerson(person);
        HuntingOrderType huntingOrderType = new HuntingOrderType();
        huntingOrder.setHuntingOrderType(huntingOrderType);
        unreviewed = new Status();
        unreviewed.setName("на рассмотрении");
        statusRepository.save(unreviewed);
        huntingOrder.setStatus(unreviewed);
        huntingOrder = huntingOrderRepository.save(huntingOrder);
        for (int i = 0; i < 2; i++) {
            HuntingOrderResource hor = new HuntingOrderResource();
            Resource resource = new Resource();
            resource.setHuntingOrderType(huntingOrderType);
            hor.setId(i);
            hor.setDistrict(new District());
            hor.setResource(resource);
            hor.setStatus(unreviewed);
            hor.setHuntingOrder(huntingOrder);
            huntingOrderResourceRepository.save(hor);
        }
    }

    @Test
    public void getResourcesByOrder_shouldReturnResources() {
        huntingOrderResources = huntingOrderResourceRepository.getResourcesByOrder(huntingOrder);

        assertThat(huntingOrderResources).isNotNull();
        assertThat(huntingOrderResources.size()).isEqualTo(2);
    }

    @Test
    public void changeResourceStatusByOrder_ShouldChangeHuntingOrderResourceStatusByOrder() {
        huntingOrderResourceRepository.changeResourceStatusByOrder(approved, huntingOrder);
        entityManager.clear();

        huntingOrderResources = huntingOrderResourceRepository.getResourcesByOrder(huntingOrder);

        for (HuntingOrderResource hor : huntingOrderResources) {
            assertThat(hor.getStatus().getName()).isEqualTo(approved.getName());
        }
    }

    @Test
    public void changeResourceStatus_ShouldChangeHuntingOrderResourceStatus() {
        var huntingOrderResourceList = huntingOrderResourceRepository.getResourcesByOrder(huntingOrder);
        var huntingOrderResource = huntingOrderResourceList.stream().findFirst().get();

        huntingOrderResourceRepository.changeResourceStatus(approved, huntingOrderResource.getId());
        entityManager.clear();

        var resultHor = huntingOrderResourceRepository.findById(huntingOrderResource.getId()).get();

        assertThat(resultHor.getStatus().getName()).isEqualTo(approved.getName());
    }

    @Test
    public void getAllResourcesByPersonAndStatus_ShouldReturnAllResourcesByStatusAndPerson() {
        var expectedResources = huntingOrderResourceRepository
                .getAllResourcesByPersonAndStatus(huntingOrder.getPerson(), unreviewed);

        assertThat(expectedResources.size()).isEqualTo(1);
    }
}