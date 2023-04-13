package ru.hunt.Request.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hunt.Request.model.HuntingOrder;
import ru.hunt.Request.model.Status;
import ru.hunt.Request.service.HuntingOrderService;
import ru.hunt.Request.service.RequestService;
import ru.hunt.Request.service.StatusService;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private HuntingOrderService huntingOrderService;

    @Autowired
    private StatusService statusService;

    @GetMapping("/test")
    public List<HuntingOrder> getUnreviewedOrders() {
        Status status = statusService.getStatusByName("на рассмотрении");
        List<HuntingOrder> result = huntingOrderService.getUnreviewedSortedOrders(status);
        return result;
    }
}