package io.bootify.planning.repos;

import io.bootify.my_app.model.PlanningWorkOrderResponse;
import io.bootify.planning.domain.PlanningWorkOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanningWorkOrderRepository extends JpaRepository<PlanningWorkOrder, String> {
@Query(value = "select PO_ID from planning_db.planning_production_order ;",nativeQuery = true)
    List<String> findAllProductionOrderIds();
@Query(value = "select * from planning_db.planning_work_order where  PRODUCT_ORDER_ID = ?1 ;",nativeQuery = true)
List<PlanningWorkOrder> getByPRODUCT_ORDER_ID(String productOrderId);
    @Query(
            value = "SELECT a.*, b.customer_code AS customerCode, b.customer_name AS customerName " +
                    "FROM planning_db.planning_work_order a " +
                    "LEFT JOIN planning_db.product_order_item b " +
                    "ON b.product_code = a.product_code AND b.product_order_id = a.product_order_id AND b.bom_version = a.bom_version " +
                    "WHERE (:woId IS NULL OR a.wo_id LIKE %:woId%) " +
                    "AND (:productCode IS NULL OR a.product_code LIKE %:productCode%) " +
                    "AND (:sapWoId IS NULL OR a.sap_wo_id LIKE %:sapWoId%) " +
                    "AND (:lotNumber IS NULL OR a.lot_number LIKE %:lotNumber%)",
            countQuery = "SELECT COUNT(*) FROM planning_db.planning_work_order a " +
                    "WHERE (:woId IS NULL OR a.wo_id LIKE %:woId%) " +
                    "AND (:productCode IS NULL OR a.product_code LIKE %:productCode%) " +
                    "AND (:sapWoId IS NULL OR a.sap_wo_id LIKE %:sapWoId%) " +
                    "AND (:lotNumber IS NULL OR a.lot_number LIKE %:lotNumber%)",
            nativeQuery = true
    )
    Page<PlanningWorkOrderResponse> findAllProjected(
            @Param("woId") String woId,
            @Param("productCode") String productCode,
            @Param("sapWoId") String sapWoId,
            @Param("lotNumber") String lotNumber,
            Pageable pageable
    );



    public Page<PlanningWorkOrder> findAll(Specification<PlanningWorkOrder> spec, Pageable pageRequest);
}
