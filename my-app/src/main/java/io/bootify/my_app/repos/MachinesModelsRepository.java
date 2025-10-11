package io.bootify.my_app.repos;

import io.bootify.my_app.domain.MachinesModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MachinesModelsRepository extends JpaRepository<MachinesModels, Integer> {
    MachinesModels findFirstByMachineName(String machineName);
    MachinesModels findFirstByMachineGroupMachineGroupId(Integer machineGroupId);

    MachinesModels findFirstByLineLineId(Integer lineId);
    @Query(value = "SELECT * FROM MachinesModels m WHERE m.MachineGroupID = ?1 AND m.LineId = 58 ;",nativeQuery = true)
    List<MachinesModels> findByMachineGroupIdAndFixedLineId(Integer machineGroupId);
    List<MachinesModels> findAllByMachineNameAndStageId(String machineName, Integer stageId);

@Transactional
    @Modifying
    @Query(value = "insert into woMachineDetail (machineName,machineGroupId,stageId,lineId,status) values" +
            "(:machineName,:machineGroupId,:stageId,:lineId,:status)",nativeQuery = true)
    void insertMachine(@Param("machineName") String machineName,
                       @Param("machineGroupId") Integer machineGroupId,
                       @Param("stageId") Integer stageId,
                       @Param("lineId") Integer lineId,
                       @Param("status") Integer status);

}
