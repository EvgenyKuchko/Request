package ru.hunt.Request.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import ru.hunt.Request.model.*;
import ru.hunt.Request.repository.HuntingOrderResourceRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class HuntingOrderResourceServiceTest {

    @InjectMocks
    private HuntingOrderResourceService huntingOrderResourceService;

    @Mock
    private HuntingOrderResourceRepository huntingOrderResourceRepository;

    private static List<HuntingOrderResource> resources;
    private static final String TYPE = "usual";
    private static HuntingOrder huntingOrder;
    private static Status status;
    private static HuntingOrderResource huntingOrderResource;

    @Before
    public void setUp() {
        resources = new ArrayList<>();
        Resource resource = new Resource();
        HuntingOrderType orderType = new HuntingOrderType();
        orderType.setType(TYPE);
        resource.setHuntingOrderType(orderType);
        HuntingOrderResource hor1 = new HuntingOrderResource();
        hor1.setResource(resource);
        HuntingOrderResource hor2 = new HuntingOrderResource();
        hor2.setResource(resource);
        HuntingOrderResource hor3 = new HuntingOrderResource();
        hor3.setResource(resource);
        resources.add(hor1);
        resources.add(hor2);
        resources.add(hor3);
        huntingOrder = new HuntingOrder();
        status = new Status();
        huntingOrderResource = new HuntingOrderResource();
        Resource res = new Resource();
        res.setStart(LocalDate.ofEpochDay(5 - 1 - 2000));
        res.setFinish(LocalDate.ofEpochDay(10 - 1 - 2000));
        huntingOrderResource.setResource(res);
        huntingOrderResource.setId(0L);
    }

    @Test
    public void checkOrderType_ShouldReturnTrue() {
        boolean expectedTrue = huntingOrderResourceService.checkOrderType(resources, TYPE);

        assertThat(expectedTrue).isEqualTo(true);
    }

    @Test
    public void checkOrderType_ShouldReturnFalse() {
        HuntingOrderType orderType = new HuntingOrderType();
        orderType.setType("unusual");
        Resource resource = new Resource();
        resource.setHuntingOrderType(orderType);
        HuntingOrderResource hor4 = new HuntingOrderResource();
        hor4.setResource(resource);
        resources.add(hor4);

        boolean expectedTrue = huntingOrderResourceService.checkOrderType(resources, TYPE);

        assertThat(expectedTrue).isEqualTo(false);
    }

    @Test
    public void getResourcesByOrder_ShouldReturnResources() {
        when(huntingOrderResourceRepository.getResourcesByOrder(huntingOrder)).thenReturn(resources);

        List<HuntingOrderResource> expectedResources = huntingOrderResourceService.getResourcesByOrder(huntingOrder);

        assertThat(resources.size()).isEqualTo(expectedResources.size());
    }

    @Test
    public void changeResourcesStatusByOrder_ShouldCallRepositoryAndChangeStatus() {
        doNothing().when(huntingOrderResourceRepository).changeResourceStatusByOrder(status, huntingOrder);

        huntingOrderResourceService.changeResourcesStatusByOrder(huntingOrder, status);

        verify(huntingOrderResourceRepository, times(1)).changeResourceStatusByOrder(status, huntingOrder);
    }

    @Test
    public void checkDate_shouldReturnTrue() {
        boolean expectedTrue = huntingOrderResourceService.checkDate(LocalDate.ofEpochDay(5 - 1 - 2000), huntingOrderResource);

        assertThat(expectedTrue).isEqualTo(true);
    }

    @Test
    public void checkDate_shouldReturnFalse() {
        boolean expectedTrue = huntingOrderResourceService.checkDate(LocalDate.ofEpochDay(2 - 1 - 2000), huntingOrderResource);

        assertThat(expectedTrue).isEqualTo(false);
    }

    @Test
    public void changeResourceStatus_ShouldCallRepositoryAndChangeStatus() {
        doNothing().when(huntingOrderResourceRepository).changeResourceStatus(status, huntingOrderResource.getId());

        huntingOrderResourceService.changeResourceStatus(huntingOrderResource, status);

        verify(huntingOrderResourceRepository, times(1)).changeResourceStatus(status, huntingOrderResource.getId());
    }

    @Test
    public void getAllAcceptedResourcesByPerson_shouldReturnList() {
        Person person = new Person();

        when(huntingOrderResourceRepository.getAllResourcesByPersonAndStatus(person, status)).thenReturn(resources);

        List<HuntingOrderResource> expectedList = huntingOrderResourceService.getAllAcceptedResourcesByPerson(person, status);

        assertThat(resources).isEqualTo(expectedList);
        assertThat(expectedList).isNotNull();
    }
}