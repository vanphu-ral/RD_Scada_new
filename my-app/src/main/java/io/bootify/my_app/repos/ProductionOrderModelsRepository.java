package io.bootify.my_app.repos;

import io.bootify.my_app.domain.ProductionOrderModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductionOrderModelsRepository extends JpaRepository<ProductionOrderModels, Integer> {

    ProductionOrderModels findFirstByMachineGroupMachineGroupId(Integer machineGroupId);

    ProductionOrderModels findFirstByProductProductId(Integer productId);
    List<ProductionOrderModels> findAllByWorkOrder(String workOrder);
    @Query(value = "SELECT top 1 a.working FROM ProductionOrderModels a " +
            "inner join MachinesModels b on b.MachineGroupID = a.MachineGroupID WHERE a.workOrder = ?1 ORDER BY b.StageID DESC ", nativeQuery = true)
    String getStatusByWorkOrderWithMaxStage(String workOrder);
}
