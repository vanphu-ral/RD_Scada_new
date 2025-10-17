package io.bootify.my_app.repos;

import io.bootify.my_app.domain.DetailQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailQuantityRepository extends JpaRepository<DetailQuantity, Long> {
    List<DetailQuantity> findAllByWorkOrderAndMachineName(String workOrder,String machineName);
    @Query(value = "SELECT SUM(dq.numberOutput) AS TotalOutput\n" +
            "FROM DetailQuantity dq\n" +
            "INNER JOIN woMachineDetail mm ON dq.machineName = mm.machineName\n" +
            "WHERE dq.workOrder = ?1 \n" +
            "  AND mm.StageID = (\n" +
            "      SELECT MAX(stageId)\n" +
            "      FROM woMachineDetail\n" +
            "      WHERE workOrder = dq.workOrder\n" +
            "  )\n", nativeQuery = true)
    Integer sumQuantityOutByWorkOrder(String workOrder);
    @Query(value = "SELECT SUM(dq.numberOutput) AS TotalOutput\n" +
            "FROM DetailQuantity dq\n" +
            "INNER JOIN woMachineDetail mm ON dq.machineName = mm.machineName\n" +
            "WHERE dq.workOrder = ?1 \n" +
            "  AND mm.StageID = (\n" +
            "      SELECT MIN(stageId)\n" +
            "      FROM woMachineDetail \n" +
            "      WHERE workOrder = dq.workOrder\n" +
            "  )\n", nativeQuery = true)
    Integer sumQuantityInByWorkOrder(String workOrder);
}
