package io.bootify.my_app.repos;

import io.bootify.my_app.domain.ScanSerialCheck;
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

    List<ScanSerialCheck> findAllBySerialItem(String serialItem);


    // Lấy 1 bản ghi theo serialBoard
    List<ScanSerialCheck> findAllBySerialBoard(String serialBoard);
//    List<ScanSerialCheck> findAllByWorkOrderAndMachineId(String workOrder, Integer machineId);

}
