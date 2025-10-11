package io.bootify.my_app.rest;

import io.bootify.my_app.domain.PlanningWO;
import io.bootify.my_app.model.*;
import io.bootify.my_app.service.PlanningWOService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/planningwo", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlanningwoResource {
    private final PlanningWOService planningWOService;

    public PlanningwoResource(PlanningWOService planningWOService) {
        this.planningWOService = planningWOService;
    }
    @GetMapping("/info/{id}")
    public ResponseEntity<?> getWoInfo(@PathVariable Long id) {
        return ResponseEntity.ok(planningWOService.getWoInfo(id));
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getWoDetailInfo(@PathVariable Long id) {
        return ResponseEntity.ok(planningWOService.getWoDetaillnfo(id));
    }
    @GetMapping("/error/info/{id}")
    public ResponseEntity<?> getWoErroeInfo(@PathVariable Long id) {
        return ResponseEntity.ok(planningWOService.getWoErrorInfo(id));
    }

    @GetMapping
    public ResponseEntity<Page<PlanningWO>> getPlanningWOs(
            @RequestParam(required = false) String branchCode,
            @RequestParam(required = false) String productCode,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String planningWorkOrderId,
            @RequestParam(required = false) String sapWoId,
            @RequestParam(required = false) String productOrderId,
            @RequestParam(required = false) String groupName,
            @RequestParam(required = false) String groupCode,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String branchName,
            @RequestParam(required = false) String lotNumber,
            @RequestParam(required = false) String woId,
            @RequestParam(required = false) BigDecimal quantityPlan,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PlanningWOFilter filter = new PlanningWOFilter();
        filter.setBranchCode(branchCode);
        filter.setProductCode(productCode);
        filter.setStatus(status);
        filter.setPlanningWorkOrderId(planningWorkOrderId);
        filter.setSapWoId(sapWoId);
        filter.setProductOrderId(productOrderId);
        filter.setGroupName(groupName);
        filter.setGroupCode(groupCode);
        filter.setProductName(productName);
        filter.setBranchName(branchName);
        filter.setLotNumber(lotNumber);
        filter.setWoId(woId);
        filter.setQuantityPlan(quantityPlan);



        Pageable pageable = PageRequest.of(page, size, Sort.by("startTime").descending());
        Page<PlanningWO> result = planningWOService.getFilteredPlanningWOs(filter, pageable);
        return ResponseEntity.ok(result);
    }
    @PostMapping
    public ResponseEntity<?> createPlanningWO(@RequestBody PlanningWO planningWO) {
        return planningWOService.create(planningWO);
    }
    @GetMapping("/serial-item")
    public ResponseEntity<ProductOrderModelsResponse> getWoErrorInfoSerialItem(
            @RequestParam(required = false) String serialItem) {

        if ((serialItem == null || serialItem.isEmpty())) {
            return ResponseEntity.badRequest().body(null);
        }

        ProductOrderModelsResponse response = planningWOService.getWoErrorCommonScadaInfoserialItem(serialItem);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/check")
    public ResponseEntity<SerialCheckResponse> checkSerialItemExist(
            @RequestBody SerialCheckRequest request) {

        ResponseEntity<SerialCheckResponse> result = planningWOService.checkSerialItemExist(request);
        return result;
    }
    @PostMapping("/insert")
    public ResponseEntity<InsertMachineResultDTO> insertMachine(
            @RequestBody List<MachinesModelsDTO> request) {
        ResponseEntity<InsertMachineResultDTO> result = planningWOService.insertMachine(request);
        return result;
    }
    @GetMapping("/serial-board")
    public ResponseEntity<ProductOrderModelsResponse> getWoErrorInfoSerialBoard(
            @RequestParam(required = false) String serialBoard) {

        if ((serialBoard == null || serialBoard.isEmpty())) {
            return ResponseEntity.badRequest().body(null);
        }

        ProductOrderModelsResponse response = planningWOService.getWoErrorCommonScadaInfoserialBoard(serialBoard);
        return ResponseEntity.ok(response);
    }

}
