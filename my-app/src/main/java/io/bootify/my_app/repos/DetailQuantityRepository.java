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
            "INNER JOIN (\n" +
            "    SELECT machineName, workOrder\n" +
            "    FROM woMachineDetail\n" +
            "    WHERE stageId = (\n" +
            "        SELECT MAX(stageId)\n" +
            "        FROM woMachineDetail\n" +
            "        WHERE workOrder = ?1 \n" +
            "           and status = 1 \n" +
            "    )\n" +
            "    AND workOrder = ?1 \n" +
            ") AS maxStage ON dq.machineName = maxStage.machineName\n" +
            "             AND dq.workOrder = maxStage.workOrder;", nativeQuery = true)
    Integer sumQuantityOutByWorkOrder(String workOrder);
    @Query(value = "SELECT SUM(dq.numberOutput) AS TotalOutput\n" +
            "FROM DetailQuantity dq\n" +
            "INNER JOIN (\n" +
            "    SELECT machineName, workOrder\n" +
            "    FROM woMachineDetail\n" +
            "    WHERE stageId = (\n" +
            "        SELECT MIN(stageId)\n" +
            "        FROM woMachineDetail\n" +
            "        WHERE workOrder = ?1 " +
            "           and status = 1 \n" +
            "    )\n" +
            "    AND workOrder = ?1 \n" +
            ") AS maxStage ON dq.machineName = maxStage.machineName\n" +
            "             AND dq.workOrder = maxStage.workOrder;", nativeQuery = true)
    Integer sumQuantityInByWorkOrder(String workOrder);
}
