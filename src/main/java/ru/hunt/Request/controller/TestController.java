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

    @GetMapping("/start")
    public String getStart() {
        requestService.checkRequests();
        return "completed";
    }

    @GetMapping("/unreviewed")
    public List<HuntingOrder> getUnreviewedOrders() {
        Status status = statusService.getStatusByName("на рассмотрении");
        return huntingOrderService.getUnreviewedSortedOrders(status);
    }

    @GetMapping("/accepted")
    public List<HuntingOrder> getAcceptedOrders() {
        Status status = statusService.getStatusByName("одобрено");
        return huntingOrderService.getUnreviewedSortedOrders(status);
    }

    @GetMapping("/p_accepted")
    public List<HuntingOrder> getPartiallyAcceptedOrders() {
        Status status = statusService.getStatusByName("частично одобрено");
        return huntingOrderService.getUnreviewedSortedOrders(status);
    }
}