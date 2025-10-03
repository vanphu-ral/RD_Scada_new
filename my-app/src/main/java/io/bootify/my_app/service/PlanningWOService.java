package io.bootify.my_app.service;

import io.bootify.my_app.domain.PlanningWO;
import io.bootify.my_app.model.PlanningWOFilter;
import io.bootify.my_app.repos.PlanningwoRepository;
import io.bootify.planning.repos.PlanningWorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanningWOService {
    @Autowired
    PlanningwoRepository planningwoRepository;

    public Page<PlanningWO> getFilteredPlanningWOs(PlanningWOFilter filter, Pageable pageable) {
        Specification<PlanningWO> spec = Specification.where(null);

        if (filter.getBranchCode() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("branchCode"), filter.getBranchCode()));
        }
        if (filter.getProductCode() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("productCode"), filter.getProductCode()));
        }

        if (filter.getStatus() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), filter.getStatus()));
        }
        // Add more filters as needed...

        return planningwoRepository.findAll(spec, pageable);
    }
    public void create(PlanningWO planningWO) {
         planningwoRepository.save(planningWO);
    }
}
