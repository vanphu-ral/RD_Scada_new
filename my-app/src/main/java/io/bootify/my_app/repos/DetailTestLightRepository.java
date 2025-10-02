package io.bootify.my_app.repos;

import io.bootify.my_app.domain.DetailTestLight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailTestLightRepository extends JpaRepository<DetailTestLight, Long> {

    DetailTestLight findFirstByProductionOrderProductionOrderId(Integer productionOrderId);

}
