package io.bootify.my_app.service;

import io.bootify.planning.domain.PlanningWorkOrder;
import io.bootify.planning.repos.PlanningWorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public Page<PlanningWorkOrder> getLatestWorkOrders(
        int pageNumber,
        String woId,
        String productCode,
        String sapWoId,
        String lotNumber
) {
    // Sắp xếp theo createTime giảm dần (mới nhất lên đầu)
    Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, "createTime"));

    Specification<PlanningWorkOrder> spec = Specification.where(
            (root, query, cb) -> cb.equal(root.get("workOrderType"), "LINE") // mặc định lọc theo workOrderType = 'LINE'
    );

    if (woId != null && !woId.isEmpty()) {
        spec = spec.and((root, query, cb) -> cb.equal(root.get("woId"), woId));
    }

    if (productCode != null && !productCode.isEmpty()) {
        spec = spec.and((root, query, cb) -> cb.equal(root.get("productCode"), productCode));
    }

    if (sapWoId != null && !sapWoId.isEmpty()) {
        spec = spec.and((root, query, cb) -> cb.equal(root.get("sapWoId"), sapWoId));
    }

    if (lotNumber != null && !lotNumber.isEmpty()) {
        spec = spec.and((root, query, cb) -> cb.equal(root.get("lotNumber"), lotNumber));
    }

    return planningWorkOrderRepository.findAll(spec, pageable);
}

    public List<String> getAllProductionOrderIds() {
        return planningWorkOrderRepository.findAllProductionOrderIds();
    }
    public List<PlanningWorkOrder> getByPRODUCT_ORDER_ID(String productOrderId) {
        return planningWorkOrderRepository.getByPRODUCT_ORDER_ID(productOrderId);
    }
}
