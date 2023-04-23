package ru.hunt.Request.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import ru.hunt.Request.model.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class RequestServiceTest {

    @InjectMocks
    private RequestService requestService;

    @Mock
    private HuntingOrderService huntingOrderService;

    @Mock
    private HuntingOrderResourceService huntingOrderResourceService;

    @Mock
    private StatusService statusService;

    @Mock
    private ResourceService resourceService;

    private static final String NOT_APPROVED_STATUS = "не одобрено";
    private static final String APPROVED_STATUS = "одобрено";
    private static final String PARTIALLY_APPROVED_STATUS = "частично одобрено";

    private HuntingOrder huntingOrder;
    private List<HuntingOrderResource> orderResources;
    private Status status;

    @Before
    public void setUp() {
        status = new Status();
        HuntingOrderType huntingOrderType = new HuntingOrderType();
        huntingOrderType.setType("type");
        huntingOrder = new HuntingOrder();
        huntingOrder.setHuntingOrderType(huntingOrderType);
        orderResources = new ArrayList<>();
        HuntingOrderResource hor1 = new HuntingOrderResource();
        HuntingOrderResource hor2 = new HuntingOrderResource();
        HuntingOrderResource hor3 = new HuntingOrderResource();
        orderResources.add(hor1);
        orderResources.add(hor2);
        orderResources.add(hor3);
    }

    @Test
    public void testCheckType_IfTrue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method checkTypeMethod = RequestService.class.getDeclaredMethod("checkType", List.class, HuntingOrder.class);
        checkTypeMethod.setAccessible(true);
        when(huntingOrderResourceService.checkOrderType(orderResources, huntingOrder.getHuntingOrderType().getType())).thenReturn(true);

        checkTypeMethod.invoke(requestService, orderResources, huntingOrder);

        verify(statusService, times(0)).getStatusByName(NOT_APPROVED_STATUS);
        verify(huntingOrderResourceService, times(0)).changeResourcesStatusByOrder(huntingOrder, status);
        verify(huntingOrderService, times(0)).changeOrderStatus(huntingOrder, statusService.getStatusByName(NOT_APPROVED_STATUS));
    }

    @Test
    public void testCheckType_IfFalse() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method checkTypeMethod = RequestService.class.getDeclaredMethod("checkType", List.class, HuntingOrder.class);
        checkTypeMethod.setAccessible(true);
        Status notApproved = new Status();
        notApproved.setName(NOT_APPROVED_STATUS);
        when(statusService.getStatusByName(NOT_APPROVED_STATUS)).thenReturn(notApproved);
        when(huntingOrderResourceService.checkOrderType(orderResources, huntingOrder.getHuntingOrderType().getType())).thenReturn(false);
        doNothing().when(huntingOrderResourceService).changeResourcesStatusByOrder(huntingOrder, notApproved);
        doNothing().when(huntingOrderService).changeOrderStatus(huntingOrder, notApproved);

        checkTypeMethod.invoke(requestService, orderResources, huntingOrder);

        verify(statusService, times(1)).getStatusByName(NOT_APPROVED_STATUS);
        verify(huntingOrderResourceService, times(1)).changeResourcesStatusByOrder(huntingOrder, statusService.getStatusByName(NOT_APPROVED_STATUS));
        verify(huntingOrderService, times(1)).changeOrderStatus(huntingOrder, statusService.getStatusByName(NOT_APPROVED_STATUS));
    }

    @Test
    public void CheckOrderByDate_CorrectDates() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method checkOrderMethod = RequestService.class.getDeclaredMethod("checkOrderByDate", LocalDate.class, List.class);
        checkOrderMethod.setAccessible(true);
        LocalDate date = LocalDate.now();
        Status unreviewed = new Status();
        unreviewed.setName("на рассмотрении");
        HuntingOrderResource hor1 = new HuntingOrderResource();
        hor1.setStatus(unreviewed);
        HuntingOrderResource hor2 = new HuntingOrderResource();
        hor2.setStatus(unreviewed);
        List<HuntingOrderResource> resourceList = new ArrayList<>();
        resourceList.add(hor1);
        resourceList.add(hor2);
        Status notApproved = new Status();
        notApproved.setName(NOT_APPROVED_STATUS);
        when(huntingOrderResourceService.checkDate(date, hor1)).thenReturn(true);
        when(huntingOrderResourceService.checkDate(date, hor2)).thenReturn(true);

        checkOrderMethod.invoke(requestService, date, resourceList);

        verify(huntingOrderResourceService, times(2)).checkDate(date, hor1);
        verify(huntingOrderResourceService, times(0)).changeResourceStatus(hor1, notApproved);
        assertThat(resourceList.size()).isEqualTo(2);
    }

    @Test
    public void CheckOrderByDate_wrongDates() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method checkOrderMethod = RequestService.class.getDeclaredMethod("checkOrderByDate", LocalDate.class, List.class);
        checkOrderMethod.setAccessible(true);
        LocalDate date = LocalDate.now();
        Status notApproved = new Status();
        notApproved.setName(NOT_APPROVED_STATUS);
        HuntingOrderResource hor1 = new HuntingOrderResource();
        hor1.setStatus(notApproved);
        HuntingOrderResource hor2 = new HuntingOrderResource();
        hor2.setStatus(notApproved);
        List<HuntingOrderResource> resourceList = new ArrayList<>();
        resourceList.add(hor1);
        resourceList.add(hor2);
        notApproved.setName(NOT_APPROVED_STATUS);
        when(statusService.getStatusByName(NOT_APPROVED_STATUS)).thenReturn(notApproved);
        when(huntingOrderResourceService.checkDate(date, hor1)).thenReturn(false);

        checkOrderMethod.invoke(requestService, date, resourceList);

        verify(huntingOrderResourceService, times(2)).checkDate(date, hor1);
        verify(huntingOrderResourceService, times(2)).changeResourceStatus(hor1, notApproved);
        assertEquals(resourceList.size(), 0);
    }

    @Test
    public void testCheckDistrict_NoResources() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method checkDistrictMethod = RequestService.class.getDeclaredMethod("checkDistrict", HuntingOrder.class, List.class);
        checkDistrictMethod.setAccessible(true);
        Person person = new Person();
        List<HuntingOrderResource> hor = new ArrayList<>();
        HuntingOrderResource res = new HuntingOrderResource();

        checkDistrictMethod.invoke(requestService, huntingOrder, hor);

        verify(huntingOrderResourceService, times(0)).getAllAcceptedResourcesByPerson(person, statusService.getStatusByName(APPROVED_STATUS));
        verify(huntingOrderResourceService, times(0)).changeResourceStatus(res, statusService.getStatusByName(NOT_APPROVED_STATUS));
        assertThat(hor.isEmpty()).isTrue();
    }

    @Test
    public void testCheckDistrict_NoAcceptedResources() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method checkDistrictMethod = RequestService.class.getDeclaredMethod("checkDistrict", HuntingOrder.class, List.class);
        checkDistrictMethod.setAccessible(true);
        HuntingOrder ho = new HuntingOrder();
        Person person = new Person();
        person.setId(1L);
        ho.setPerson(person);
        List<HuntingOrderResource> hor = new ArrayList<>();
        HuntingOrderResource hor1 = new HuntingOrderResource();
        Resource res1 = new Resource();
        res1.setName("resource1");
        hor1.setResource(res1);
        hor.add(hor1);

        when(huntingOrderResourceService.getAllAcceptedResourcesByPerson(person, statusService.getStatusByName(APPROVED_STATUS))).thenReturn(new ArrayList<>());

        checkDistrictMethod.invoke(requestService, ho, hor);

        verify(huntingOrderResourceService, times(1)).getAllAcceptedResourcesByPerson(person, statusService.getStatusByName(APPROVED_STATUS));
        verify(huntingOrderResourceService, times(0)).changeResourceStatus(hor1, statusService.getStatusByName(NOT_APPROVED_STATUS));
        assertEquals(1, hor.size());
    }

    @Test
    public void testCheckDistrictMatch() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method checkDistrictMethod = RequestService.class.getDeclaredMethod("checkDistrict", HuntingOrder.class, List.class);
        checkDistrictMethod.setAccessible(true);
        HuntingOrder ho = new HuntingOrder();
        Person person = new Person();
        ho.setPerson(person);
        Resource resource1 = new Resource();
        resource1.setName("Resource1");
        Resource resource2 = new Resource();
        resource2.setName("Resource2");
        Status approved = new Status();
        approved.setName(APPROVED_STATUS);
        Status notApproved = new Status();
        notApproved.setName(NOT_APPROVED_STATUS);
        HuntingOrderResource acceptedHor = new HuntingOrderResource();
        acceptedHor.setResource(resource1);
        acceptedHor.setStatus(approved);
        List<HuntingOrderResource> acceptedHorList = new ArrayList<>();
        acceptedHorList.add(acceptedHor);
        HuntingOrderResource hor1 = new HuntingOrderResource();
        hor1.setResource(resource1);
        HuntingOrderResource hor2 = new HuntingOrderResource();
        hor2.setResource(resource2);
        List<HuntingOrderResource> hor = new ArrayList<>(Arrays.asList(hor1, hor2));
        when(statusService.getStatusByName(APPROVED_STATUS)).thenReturn(approved);
        when(huntingOrderResourceService.getAllAcceptedResourcesByPerson(person, approved)).thenReturn(acceptedHorList);

        checkDistrictMethod.invoke(requestService, ho, hor);

        verify(huntingOrderResourceService, times(1)).getAllAcceptedResourcesByPerson(person, statusService.getStatusByName(APPROVED_STATUS));
        verify(huntingOrderResourceService, times(1)).changeResourceStatus(hor1, statusService.getStatusByName(NOT_APPROVED_STATUS));
    }

    @Test
    public void checkResourceQuota_NoResources() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method checkResourceQuotaMethod = RequestService.class.getDeclaredMethod("checkResourceQuota", List.class, HuntingOrder.class);
        checkResourceQuotaMethod.setAccessible(true);
        Resource resource = new Resource();
        List<HuntingOrderResource> hor = new ArrayList<>();

        checkResourceQuotaMethod.invoke(requestService, hor, huntingOrder);

        verify(huntingOrderResourceService, times(0)).changeResourcesStatusByOrder(huntingOrder, statusService.getStatusByName(NOT_APPROVED_STATUS));
        verify(huntingOrderResourceService, times(0)).changeResourcesStatusByOrder(huntingOrder, statusService.getStatusByName(APPROVED_STATUS));
        verify(resourceService, times(0)).changeAmount(resource, 1);
        assertThat(hor.isEmpty()).isTrue();
    }

    @Test
    public void checkResourceQuota_ResourceAmountIsMoreThanHuntingOrderResourceAmount() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method checkResourceQuotaMethod = RequestService.class.getDeclaredMethod("checkResourceQuota", List.class, HuntingOrder.class);
        checkResourceQuotaMethod.setAccessible(true);
        Resource resource = new Resource();
        resource.setAmount(10);
        List<HuntingOrderResource> huntingOrderResources = new ArrayList<>();
        HuntingOrderResource hor = new HuntingOrderResource();
        hor.setAmount(5);
        hor.setResource(resource);
        huntingOrderResources.add(hor);
        Status status = new Status();
        when(statusService.getStatusByName(APPROVED_STATUS)).thenReturn(status);
        doNothing().when(huntingOrderResourceService).changeResourcesStatusByOrder(huntingOrder, status);
        doNothing().when(resourceService).changeAmount(resource, hor.getAmount());

        checkResourceQuotaMethod.invoke(requestService, huntingOrderResources, huntingOrder);

        verify(huntingOrderResourceService, times(0)).changeResourcesStatusByOrder(huntingOrder, statusService.getStatusByName(NOT_APPROVED_STATUS));
        verify(huntingOrderResourceService, times(1)).changeResourcesStatusByOrder(huntingOrder, statusService.getStatusByName(APPROVED_STATUS));
        verify(resourceService, times(1)).changeAmount(resource, hor.getAmount());
        assertThat(huntingOrderResources.isEmpty()).isFalse();
    }

    @Test
    public void checkResourceQuota_ResourceAmountIsLessThanHuntingOrderResourceAmount() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method checkResourceQuotaMethod = RequestService.class.getDeclaredMethod("checkResourceQuota", List.class, HuntingOrder.class);
        checkResourceQuotaMethod.setAccessible(true);
        Resource resource = new Resource();
        resource.setAmount(2);
        List<HuntingOrderResource> huntingOrderResources = new ArrayList<>();
        HuntingOrderResource hor = new HuntingOrderResource();
        hor.setAmount(5);
        hor.setResource(resource);
        huntingOrderResources.add(hor);
        Status status = new Status();
        when(statusService.getStatusByName(NOT_APPROVED_STATUS)).thenReturn(status);
        doNothing().when(huntingOrderResourceService).changeResourcesStatusByOrder(huntingOrder, status);

        checkResourceQuotaMethod.invoke(requestService, huntingOrderResources, huntingOrder);

        verify(huntingOrderResourceService, times(1)).changeResourcesStatusByOrder(huntingOrder, statusService.getStatusByName(NOT_APPROVED_STATUS));
        verify(huntingOrderResourceService, times(0)).changeResourcesStatusByOrder(huntingOrder, statusService.getStatusByName(APPROVED_STATUS));
        verify(resourceService, times(0)).changeAmount(resource, hor.getAmount());
        assertThat(huntingOrderResources.isEmpty()).isTrue();
    }

    @Test
    public void checkSameResource_CountOfResourcesLessThanTwo() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method checkSameMethod = RequestService.class.getDeclaredMethod("checkSameResource", List.class);
        checkSameMethod.setAccessible(true);
        List<HuntingOrderResource> huntingOrderResources = new ArrayList<>();

        checkSameMethod.invoke(requestService, huntingOrderResources);

        verify(huntingOrderResourceService, times(0)).changeResourcesStatusByOrder(huntingOrder, statusService.getStatusByName(NOT_APPROVED_STATUS));
        assertThat(huntingOrderResources.size()).isEqualTo(0);
    }

    @Test
    public void checkSameResource_NoSameResources() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method checkSameMethod = RequestService.class.getDeclaredMethod("checkSameResource", List.class);
        checkSameMethod.setAccessible(true);
        List<HuntingOrderResource> huntingOrderResources = new ArrayList<>();
        HuntingOrderResource hor1 = new HuntingOrderResource();
        hor1.setId(1L);
        Resource bear = new Resource();
        bear.setName("медведь");
        hor1.setResource(bear);
        HuntingOrderResource hor2 = new HuntingOrderResource();
        hor2.setId(2L);
        Resource fox = new Resource();
        bear.setName("лиса");
        hor2.setResource(fox);
        huntingOrderResources.add(hor1);
        huntingOrderResources.add(hor2);

        checkSameMethod.invoke(requestService, huntingOrderResources);

        verify(huntingOrderResourceService, times(0)).changeResourceStatus(hor2, statusService.getStatusByName(NOT_APPROVED_STATUS));
        assertThat(huntingOrderResources.size()).isEqualTo(2);
    }

    @Test
    public void checkSameResource_SameResources() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method checkSameMethod = RequestService.class.getDeclaredMethod("checkSameResource", List.class);
        checkSameMethod.setAccessible(true);
        List<HuntingOrderResource> huntingOrderResources = new ArrayList<>();
        HuntingOrderResource hor1 = new HuntingOrderResource();
        hor1.setId(1L);
        Resource bear = new Resource();
        bear.setName("медведь");
        hor1.setResource(bear);
        HuntingOrderResource hor2 = new HuntingOrderResource();
        hor2.setId(2L);
        hor2.setResource(bear);
        huntingOrderResources.add(hor1);
        huntingOrderResources.add(hor2);
        Status status = new Status();
        when(statusService.getStatusByName(NOT_APPROVED_STATUS)).thenReturn(status);

        checkSameMethod.invoke(requestService, huntingOrderResources);

        verify(huntingOrderResourceService, times(1)).changeResourceStatus(hor2, statusService.getStatusByName(NOT_APPROVED_STATUS));
        assertThat(huntingOrderResources.size()).isEqualTo(1);
    }

    @Test
    public void finalCheck_approved() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method finalCheckMethod = RequestService.class.getDeclaredMethod("finalCheck", HuntingOrder.class, List.class, int.class);
        finalCheckMethod.setAccessible(true);
        List<HuntingOrderResource> huntingOrderResources = new ArrayList<>();
        HuntingOrderResource hor1 = new HuntingOrderResource();
        HuntingOrderResource hor2 = new HuntingOrderResource();
        huntingOrderResources.add(hor1);
        huntingOrderResources.add(hor2);
        var startSize = 2;
        Status approved = new Status();
        when(statusService.getStatusByName(APPROVED_STATUS)).thenReturn(approved);
        doNothing().when(huntingOrderService).changeOrderStatus(huntingOrder, approved);

        finalCheckMethod.invoke(requestService, huntingOrder, huntingOrderResources, startSize);

        verify(huntingOrderService, times(1)).changeOrderStatus(huntingOrder, statusService.getStatusByName(APPROVED_STATUS));
    }

    @Test
    public void finalCheck_partiallyApproved() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method finalCheckMethod = RequestService.class.getDeclaredMethod("finalCheck", HuntingOrder.class, List.class, int.class);
        finalCheckMethod.setAccessible(true);
        List<HuntingOrderResource> huntingOrderResources = new ArrayList<>();
        HuntingOrderResource hor1 = new HuntingOrderResource();
        huntingOrderResources.add(hor1);
        var startSize = 2;
        Status approved = new Status();
        when(statusService.getStatusByName(PARTIALLY_APPROVED_STATUS)).thenReturn(approved);
        doNothing().when(huntingOrderService).changeOrderStatus(huntingOrder, approved);

        finalCheckMethod.invoke(requestService, huntingOrder, huntingOrderResources, startSize);

        verify(huntingOrderService, times(1)).changeOrderStatus(huntingOrder, statusService.getStatusByName(PARTIALLY_APPROVED_STATUS));
    }

    @Test
    public void finalCheck_notApproved() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method finalCheckMethod = RequestService.class.getDeclaredMethod("finalCheck", HuntingOrder.class, List.class, int.class);
        finalCheckMethod.setAccessible(true);
        List<HuntingOrderResource> huntingOrderResources = new ArrayList<>();
        var startSize = 2;
        Status approved = new Status();
        when(statusService.getStatusByName(NOT_APPROVED_STATUS)).thenReturn(approved);
        doNothing().when(huntingOrderService).changeOrderStatus(huntingOrder, approved);

        finalCheckMethod.invoke(requestService, huntingOrder, huntingOrderResources, startSize);

        verify(huntingOrderService, times(1)).changeOrderStatus(huntingOrder, statusService.getStatusByName(NOT_APPROVED_STATUS));
    }
}