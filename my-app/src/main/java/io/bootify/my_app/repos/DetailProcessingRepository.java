package io.bootify.my_app.repos;

import io.bootify.my_app.domain.DetailProcessing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailProcessingRepository extends JpaRepository<DetailProcessing, Long> {

    DetailProcessing findFirstByMachineMachineId(Integer machineId);

    DetailProcessing findFirstByProductionOrderProductionOrderId(Integer productionOrderId);

}
