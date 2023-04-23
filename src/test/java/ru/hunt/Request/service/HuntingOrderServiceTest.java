package ru.hunt.Request.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import ru.hunt.Request.model.HuntingOrder;
import ru.hunt.Request.model.Status;
import ru.hunt.Request.repository.HuntingOrderRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class HuntingOrderServiceTest {

    @InjectMocks
    private HuntingOrderService huntingOrderService;

    @Mock
    private HuntingOrderRepository huntingOrderRepository;

    private static Status status;
    private static HuntingOrder huntingOrder;
    private static final long ID = 0L;
    private static List<HuntingOrder> list;

    @Before
    public void setUp() {
        status = new Status();
        huntingOrder = new HuntingOrder();
        huntingOrder.setId(ID);
        HuntingOrder ho1 = new HuntingOrder();
        ho1.setStatus(status);
        ho1.setDate(LocalDate.ofEpochDay(5 - 1 - 2000));
        HuntingOrder ho2 = new HuntingOrder();
        ho2.setStatus(status);
        ho2.setDate(LocalDate.ofEpochDay(4 - 1 - 2000));
        HuntingOrder ho3 = new HuntingOrder();
        ho3.setStatus(status);
        ho3.setDate(LocalDate.ofEpochDay(3 - 1 - 2000));
        list = new ArrayList<>();
        list.add(ho1);
        list.add(ho2);
        list.add(ho3);
    }

    @Test
    public void changeOrderStatus_shouldChangeOrderStatus() {
        doNothing().when(huntingOrderRepository).changeOrderStatus(status, huntingOrder.getId());

        huntingOrderService.changeOrderStatus(huntingOrder, status);

        verify(huntingOrderRepository, times(1)).changeOrderStatus(status, huntingOrder.getId());
    }

    @Test
    public void getUnreviewedSortedOrders_shouldGetAllOrdersThatHaveStatus() {
        when(huntingOrderRepository.getOrderByStatus(status)).thenReturn(list);

        List<HuntingOrder> sortedOrders = huntingOrderService.getUnreviewedSortedOrders(status);

        assertThat(sortedOrders.size()).isEqualTo(list.size());
        assertThat((sortedOrders.get(0))).isEqualTo(list.get(2));
    }
}