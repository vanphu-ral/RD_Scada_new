package io.bootify.my_app.rest;

import io.bootify.my_app.model.PlanningWorkOrderResponse;
import io.bootify.my_app.service.PlanningWorkOrderService;
import io.bootify.planning.domain.PlanningWorkOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/planningworkorder", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:9000")
public class PlannningWorkOrderResource {
    @Autowired
    PlanningWorkOrderService planningWorkOrderService;

//    @GetMapping
//    public Page<PlanningWorkOrder> getWorkOrders(@RequestParam(defaultValue = "0") int page) {
//        return planningWorkOrderService.getLatestWorkOrders(page);
//    }
@GetMapping("/latest")
public ResponseEntity<Page<PlanningWorkOrder>> getLatestWorkOrders(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(required = false) String woId,
        @RequestParam(required = false) String productCode,
        @RequestParam(required = false) String sapWoId,
        @RequestParam(required = false) String lotNumber
) {
    Page<PlanningWorkOrder> result = planningWorkOrderService.getLatestWorkOrders(
            page, woId, productCode, sapWoId, lotNumber
    );
    return ResponseEntity.ok(result);
}
    @GetMapping("/productionOrderIds")
    public List<String> getAllProductionOrderIds() {
        return planningWorkOrderService.getAllProductionOrderIds();
    }
    @GetMapping("/byProductionOrderId")
    public List<PlanningWorkOrder> getByProductionOrderId(@RequestParam String productOrderId) {
        return planningWorkOrderService.getByPRODUCT_ORDER_ID(productOrderId);
    }
    @GetMapping
    public Page<PlanningWorkOrderResponse> getPlanningWorkOrders(
            @RequestParam(required = false) String woId,
            @RequestParam(required = false) String productCode,
            @RequestParam(required = false) String sapWoId,
            @RequestParam(required = false) String lotNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return planningWorkOrderService.searchWorkOrders(woId, productCode, sapWoId, lotNumber, pageable);
    }
}
