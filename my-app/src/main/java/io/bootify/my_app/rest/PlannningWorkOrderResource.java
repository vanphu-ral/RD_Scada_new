package io.bootify.my_app.rest;

import io.bootify.my_app.service.PlanningWorkOrderService;
import io.bootify.planning.domain.PlanningWorkOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/planningworkorder", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlannningWorkOrderResource {
    @Autowired
    PlanningWorkOrderService planningWorkOrderService;

    @GetMapping
    public Page<PlanningWorkOrder> getWorkOrders(@RequestParam(defaultValue = "0") int page) {
        return planningWorkOrderService.getLatestWorkOrders(page);
    }
    @GetMapping("/productionOrderIds")
    public List<String> getAllProductionOrderIds() {
        return planningWorkOrderService.getAllProductionOrderIds();
    }
    @GetMapping("/byProductionOrderId")
    public List<PlanningWorkOrder> getByProductionOrderId(@RequestParam String productOrderId) {
        return planningWorkOrderService.getByPRODUCT_ORDER_ID(productOrderId);
    }
}
