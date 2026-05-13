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
    @Query(value = "select top 1 * from ProductionOrderModels where workOrder = ?1 order by ProductionOrderID desc", nativeQuery = true)
    ProductionOrderModels getByWorkOrderAndMachineGroupID(String workOrder, Integer machineGroupId);
    @Query(value = "SELECT top 1 a.working FROM ProductionOrderModels a " +
            "inner join MachinesModels b on b.MachineGroupID = a.MachineGroupID WHERE a.workOrder = ?1 ORDER BY b.StageID DESC ", nativeQuery = true)
    String getStatusByWorkOrderWithMaxStage(String workOrder);
    @Query(value ="SELECT\n" +
            "      [WorkOrder]\n" +
            "  FROM [ScadaMappingInfo].[dbo].[ProductionOrderModels] p\n" +
            "   inner join MachinesModels m on p.MachineGroupID=m.MachineGroupID\n" +
            "  where m.MachineName= ?1 and Working not in (2) group by WorkOrder ;",nativeQuery = true)
    List<String> getWorkOrderByWorkingNot2AndMachineName (String machineName);
}
