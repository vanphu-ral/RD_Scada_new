package io.bootify.my_app.service;

import io.bootify.planning.domain.PlanningWorkOrder;
import io.bootify.planning.repos.PlanningWorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanningWorkOrderService {
    @Autowired
    PlanningWorkOrderRepository planningWorkOrderRepository;

//    public List<PlanningWorkOrder> findAll() {
//        return planningWorkOrderRepository.findAll();
//    }
//    public Page<PlanningWorkOrder> getLatestWorkOrders(int pageNumber) {
//        PageRequest pageRequest = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, "createTime"));
//        return planningWorkOrderRepository.findAll(pageRequest);
//    }
    public Page<PlanningWorkOrder> getLatestWorkOrders(int pageNumber, String woId) {
        PageRequest pageRequest = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, "createTime"));

        Specification<PlanningWorkOrder> spec = Specification.where(null);

        if (woId != null && !woId.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("woId"), woId));
        }

        return planningWorkOrderRepository.findAll(spec, pageRequest);
    }
    public List<String> getAllProductionOrderIds() {
        return planningWorkOrderRepository.findAllProductionOrderIds();
    }
    public List<PlanningWorkOrder> getByPRODUCT_ORDER_ID(String productOrderId) {
        return planningWorkOrderRepository.getByPRODUCT_ORDER_ID(productOrderId);
    }
}
