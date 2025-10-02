package io.bootify.my_app.repos;

import io.bootify.my_app.domain.LedwireProcessing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LedwireProcessingRepository extends JpaRepository<LedwireProcessing, Integer> {

    LedwireProcessing findFirstByMachineMachineId(Integer machineId);

    LedwireProcessing findFirstByProductionOrderProductionOrderId(Integer productionOrderId);

}
