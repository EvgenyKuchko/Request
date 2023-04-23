package ru.hunt.Request.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hunt.Request.model.HuntingOrder;
import ru.hunt.Request.model.Status;
import ru.hunt.Request.repository.HuntingOrderRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HuntingOrderService {

    @Autowired
    private HuntingOrderRepository huntingOrderRepository;

    @Transactional
    public List<HuntingOrder> getUnreviewedSortedOrders(Status status) {
        List<HuntingOrder> orders = huntingOrderRepository.getOrderByStatus(status);
        return orders.stream()
                .sorted(Comparator.comparing(HuntingOrder::getDate))
                .collect(Collectors.toList());
    }

    @Transactional
    public void changeOrderStatus(HuntingOrder huntingOrder, Status status) {
        huntingOrderRepository.changeOrderStatus(status, huntingOrder.getId());
    }
}