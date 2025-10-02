package io.bootify.my_app.repos;

import io.bootify.my_app.domain.DetailLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailLabelRepository extends JpaRepository<DetailLabel, Long> {

    DetailLabel findFirstByProductionOrderProductionOrderId(Integer productionOrderId);

}
