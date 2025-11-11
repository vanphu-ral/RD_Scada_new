package io.bootify.my_app.repos;

import io.bootify.my_app.domain.ScanSerialCheck;
import io.bootify.my_app.model.CheckSerialResult;
import io.bootify.my_app.model.ScanSerialChecksResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScanSerialCheckRepository extends JpaRepository<ScanSerialCheck, Long> {

    ScanSerialCheck findFirstByMachineMachineId(Integer machineId);

    ScanSerialCheck findFirstByProductionOrderProductionOrderId(Integer productionOrderId);
    List<ScanSerialCheck> findAllByWorkOrder(String workOrder);

    @Query(value = "SELECT\n" +
            "\t\ta.serialID as serialId\n" +
            "      ,a.productionOrderID as productionOrderId\n" +
            "      ,a.machineID as machineId\n" +
            "      ,a.serialBoard as serialBoard\n" +
            "      ,a.serialItem as serialItem\n" +
            "      ,a.serialStatus as serialStatus\n" +
            "      ,a.serialCheck as serialCheck\n" +
            "      ,a.timeScan as timeScan\n" +
            "      ,a.timeCheck as timeCheck\n" +
            "      ,a.resultCheck as resultCheck\n" +
            "      ,a.workOrder as workOrder\n" +
            "\t  ,b.MachineName as machineName\n" +
            "\t  ,b.StageID as stageNum\n" +
            "  FROM ScanSerialCheck a\n" +
            "  left join MachinesModels b on b.MachineID =a.machineID\n" +
            "  where a.workOrder =?1 ;",nativeQuery = true)
    List<ScanSerialChecksResponse> getAllByWorkOrder(String workOrder);

    // Lấy 1 bản ghi theo serialItem
    @Query(value = "SELECT\n" +
            "\t\ta.serialID as serialId\n" +
            "      ,a.productionOrderID as productionOrderId\n" +
            "      ,a.machineID as machineId\n" +
            "      ,a.serialBoard as serialBoard\n" +
            "      ,a.serialItem as serialItem\n" +
            "      ,a.serialStatus as serialStatus\n" +
            "      ,a.serialCheck as serialCheck\n" +
            "      ,a.timeScan as timeScan\n" +
            "      ,a.timeCheck as timeCheck\n" +
            "      ,a.resultCheck as resultCheck\n" +
            "      ,a.workOrder as workOrder\n" +
            "\t  ,b.MachineName as machineName\n" +
            "\t  ,b.StageID as stageNum\n" +
            "  FROM ScanSerialCheck a\n" +
            "  left join MachinesModels b on b.MachineID =a.machineID\n" +
            "  where a.serialItem =?1  ;",nativeQuery = true)
    List<ScanSerialChecksResponse> getBySerialItem(String serialItem);


    // Lấy 1 bản ghi theo serialBoard
    @Query(value = "SELECT\n" +
            "\t\ta.serialID as serialId\n" +
            "      ,a.productionOrderID as productionOrderId\n" +
            "      ,a.machineID as machineId\n" +
            "      ,a.serialBoard as serialBoard\n" +
            "      ,a.serialItem as serialItem\n" +
            "      ,a.serialStatus as serialStatus\n" +
            "      ,a.serialCheck as serialCheck\n" +
            "      ,a.timeScan as timeScan\n" +
            "      ,a.timeCheck as timeCheck\n" +
            "      ,a.resultCheck as resultCheck\n" +
            "      ,a.workOrder as workOrder\n" +
            "\t  ,b.MachineName as machineName\n" +
            "\t  ,b.StageID as stageNum\n" +
            "  FROM ScanSerialCheck a\n" +
            "  left join MachinesModels b on b.MachineID =a.machineID\n" +
            "  where a.serialBoard =?1  ;",nativeQuery = true)
    List<ScanSerialChecksResponse> getBySerialBoard(String serialBoard);
    @Query(value = "SELECT\n" +
            "\t\ta.serialID as serialId\n" +
            "      ,a.productionOrderID as productionOrderId\n" +
            "      ,a.machineID as machineId\n" +
            "      ,a.serialBoard as serialBoard\n" +
            "      ,a.serialItem as serialItem\n" +
            "      ,a.serialStatus as serialStatus\n" +
            "      ,a.serialCheck as serialCheck\n" +
            "      ,a.timeScan as timeScan\n" +
            "      ,a.timeCheck as timeCheck\n" +
            "      ,a.resultCheck as resultCheck\n" +
            "      ,a.workOrder as workOrder\n" +
            "\t  ,b.MachineName as machineName\n" +
            "\t  ,b.StageID as stageNum\n" +
            "  FROM ScanSerialCheck a\n" +
            "  left join MachinesModels b on b.MachineID =a.machineID\n" +
            "  where a.workOrder =?1 and a.machineID =?2 ;",nativeQuery = true)
    List<ScanSerialCheck> getAllByWorkOrderAndMachineId(String workOrder, Integer machineId);
    @Query(value="SELECT \n" +
            "    b.MachineName as machineName,\n" +
            "    a.workOrder as workOrder,\n" +
            "    (SELECT COUNT(serialID) \n" +
            "     FROM [ScadaMappingInfo].[dbo].[ScanSerialCheck] \n" +
            "     WHERE workOrder = a.workOrder AND machineID = a.machineID and CASE \n" +
            "        WHEN CHARINDEX('-', serialItem) > 0 \n" +
            "        THEN REVERSE(SUBSTRING(REVERSE(serialItem), 1, CHARINDEX('-', REVERSE(serialItem)) - 1))\n" +
            "        ELSE serialItem\n" +
            "    END = CASE \n" +
            "        WHEN CHARINDEX('-', a.serialItem) > 0 \n" +
            "        THEN REVERSE(SUBSTRING(REVERSE(a.serialItem), 1, CHARINDEX('-', REVERSE(a.serialItem)) - 1))\n" +
            "        ELSE a.serialItem\n" +
            "    END) AS sumOfSerial,\n" +
            "    CASE \n" +
            "        WHEN CHARINDEX('-', a.serialItem) > 0 \n" +
            "        THEN REVERSE(SUBSTRING(REVERSE(a.serialItem), 1, CHARINDEX('-', REVERSE(a.serialItem)) - 1))\n" +
            "        ELSE a.serialItem\n" +
            "    END AS serialType,\n" +
            "\t    CASE \n" +
            "        WHEN CHARINDEX('-', a.serialItem) > 0 \n" +
            "        THEN 'true'\n" +
            "        ELSE 'false'END AS result\n" +
            "FROM  [ScadaMappingInfo].[dbo].[ScanSerialCheck] a\n" +
            "INNER JOIN MachinesModels b ON b.MachineID = a.machineID\n" +
            "where a.workOrder= ?1 \n" +
            "GROUP BY \n" +
            "    b.MachineName,\n" +
            "    a.workOrder,\n" +
            "    a.machineID,\n" +
            "    CASE \n" +
            "        WHEN CHARINDEX('-', a.serialItem) > 0 \n" +
            "        THEN REVERSE(SUBSTRING(REVERSE(a.serialItem), 1, CHARINDEX('-', REVERSE(a.serialItem)) - 1))\n" +
            "        ELSE a.serialItem\n" +
            "    END,\n" +
            "\tCASE \n" +
            "        WHEN CHARINDEX('-', a.serialItem) >0 \n" +
            "        THEN 'true'\n" +
            "        ELSE 'false'END\n" +
            "ORDER BY a.workOrder;\n",nativeQuery = true)
    public List<CheckSerialResult> checkSerials(String workOrder);

}
