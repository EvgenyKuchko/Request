package ru.hunt.Request.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hunt.Request.model.HuntingOrder;
import ru.hunt.Request.service.HuntingOrderService;
import ru.hunt.Request.service.RequestService;
import ru.hunt.Request.service.StatusService;

@RestController
@Getter
@Setter
public class ActionController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private HuntingOrderService huntingOrderService;

    @Autowired
    private StatusService statusService;

    private static final String UNREVIEWED_STATUS = "на рассмотрении";
    private static final String NOT_APPROVED_STATUS = "не одобрено";
    private static final String APPROVED_STATUS = "одобрено";
    private static final String PARTIALLY_APPROVED_STATUS = "частично одобрено";
    private boolean checkStatus = false;

    @GetMapping("/start")
    public String getStart() {
        var orders = huntingOrderService.getUnreviewedSortedOrders(statusService.getStatusByName(UNREVIEWED_STATUS));
        checkStatus = true;
        while (checkStatus) {
            for (HuntingOrder order : orders) {
                requestService.checkRequests(order);
            }
            checkStatus = false;
        }
        return "check finished";
    }

    @GetMapping("/stop")
    public String getStop() {
        if (checkStatus) {
            checkStatus = false;
            return "check stopped";
        } else {
            return "check already stopped";
        }
    }
}