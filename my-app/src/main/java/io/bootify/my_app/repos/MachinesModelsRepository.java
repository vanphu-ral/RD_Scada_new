package io.bootify.my_app.repos;

import io.bootify.my_app.domain.MachinesModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MachinesModelsRepository extends JpaRepository<MachinesModels, Integer> {

    MachinesModels findFirstByMachineGroupMachineGroupId(Integer machineGroupId);

    MachinesModels findFirstByLineLineId(Integer lineId);
    @Query(value = "SELECT * FROM MachinesModels m WHERE m.MachineGroupID = ?1 AND m.LineId = 58 ;",nativeQuery = true)
    List<MachinesModels> findByMachineGroupIdAndFixedLineId(Integer machineGroupId);




}
