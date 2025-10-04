package io.bootify.my_app.repos;

import io.bootify.my_app.domain.ScanSerialCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScanSerialCheckRepository extends JpaRepository<ScanSerialCheck, Long> {

    ScanSerialCheck findFirstByMachineMachineId(Integer machineId);

    ScanSerialCheck findFirstByProductionOrderProductionOrderId(Integer productionOrderId);
    List<ScanSerialCheck> findAllByWorkOrder(String workOrder);

}
