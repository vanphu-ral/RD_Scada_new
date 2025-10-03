package io.bootify.my_app.rest;

import io.bootify.my_app.domain.PlanningWO;
import io.bootify.my_app.model.PlanningWOFilter;
import io.bootify.my_app.service.PlanningWOService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/planningwo", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlanningwoResource {
    private final PlanningWOService planningWOService;

    public PlanningwoResource(PlanningWOService planningWOService) {
        this.planningWOService = planningWOService;
    }

    @GetMapping
    public ResponseEntity<Page<PlanningWO>> getPlanningWOs(
            @RequestParam(required = false) String branchCode,
            @RequestParam(required = false) String productCode,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PlanningWOFilter filter = new PlanningWOFilter();
        filter.setBranchCode(branchCode);
        filter.setProductCode(productCode);
        filter.setStatus(status);


        Pageable pageable = PageRequest.of(page, size, Sort.by("startTime").descending());
        Page<PlanningWO> result = planningWOService.getFilteredPlanningWOs(filter, pageable);
        return ResponseEntity.ok(result);
    }
    @PostMapping
    public ResponseEntity<String> createPlanningWO(@RequestBody PlanningWO planningWO) {
        planningWOService.create(planningWO);
        return ResponseEntity.ok("PlanningWO created successfully");
    }
}
