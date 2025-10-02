package io.bootify.my_app.repos;

import io.bootify.my_app.domain.MachinesModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachinesModelsRepository extends JpaRepository<MachinesModels, Integer> {

    MachinesModels findFirstByMachineGroupMachineGroupId(Integer machineGroupId);

    MachinesModels findFirstByLineLineId(Integer lineId);

}
