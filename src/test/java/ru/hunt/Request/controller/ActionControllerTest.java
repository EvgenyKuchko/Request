package ru.hunt.Request.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.hunt.Request.model.HuntingOrder;
import ru.hunt.Request.model.Status;
import ru.hunt.Request.service.HuntingOrderService;
import ru.hunt.Request.service.RequestService;
import ru.hunt.Request.service.StatusService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActionControllerTest {

    @Autowired
    private ActionController actionController;

    @MockBean
    private RequestService requestService;

    @MockBean
    private HuntingOrderService huntingOrderService;

    @MockBean
    private StatusService statusService;

    private static final String UNREVIEWED = "на рассмотрении";

    @Before
    public void setUp() {
        Status unreviewedStatus = new Status();
        unreviewedStatus.setName(UNREVIEWED);
        HuntingOrder order1 = new HuntingOrder();
        HuntingOrder order2 = new HuntingOrder();
        List<HuntingOrder> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        when(huntingOrderService.getUnreviewedSortedOrders(unreviewedStatus)).thenReturn(orders);
        when(statusService.getStatusByName(UNREVIEWED)).thenReturn(unreviewedStatus);
    }

    @Test
    public void testGetStart() {
        var result = actionController.getStart();

        assertEquals("check finished", result);

        verify(huntingOrderService, times(1)).getUnreviewedSortedOrders(any(Status.class));
        verify(statusService, times(1)).getStatusByName(UNREVIEWED);
        verify(requestService, times(2)).checkRequests(any(HuntingOrder.class));
    }

    @Test
    public void getStopCheckStatusTrue_ShouldStopChecking() {
        actionController.setCheckStatus(true);

        String result = actionController.getStop();

        assertEquals("check stopped", result);

        assertFalse(actionController.isCheckStatus());
    }

    @Test
    public void getStopCheckStatusFalse_ShouldDoNothing() {
        actionController.setCheckStatus(false);

        String result = actionController.getStop();

        assertEquals("check already stopped", result);

        assertFalse(actionController.isCheckStatus());
    }
}