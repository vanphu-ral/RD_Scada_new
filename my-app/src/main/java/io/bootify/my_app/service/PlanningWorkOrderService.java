package io.bootify.my_app.service;

import io.bootify.planning.domain.PlanningWorkOrder;
import io.bootify.planning.repos.PlanningWorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanningWorkOrderService {
    @Autowired
    PlanningWorkOrderRepository planningWorkOrderRepository;

    public List<PlanningWorkOrder> findAll() {
        return planningWorkOrderRepository.findAll();
    }
    public Page<PlanningWorkOrder> getLatestWorkOrders(int pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, "createTime"));
        return planningWorkOrderRepository.findAll(pageRequest);
    }
}
