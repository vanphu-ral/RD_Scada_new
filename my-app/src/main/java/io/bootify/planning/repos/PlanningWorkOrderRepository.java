package io.bootify.planning.repos;

import io.bootify.planning.domain.PlanningWorkOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanningWorkOrderRepository extends JpaRepository<PlanningWorkOrder, String> {
@Query(value = "select PO_ID from planning_db.planning_production_order ;",nativeQuery = true)
    List<String> findAllProductionOrderIds();
@Query(value = "select * from planning_db.planning_work_order where  PRODUCT_ORDER_ID = ?1 ;",nativeQuery = true)
List<PlanningWorkOrder> getByPRODUCT_ORDER_ID(String productOrderId);

    Page<PlanningWorkOrder> findAll(Specification<PlanningWorkOrder> spec, PageRequest pageRequest);
}
