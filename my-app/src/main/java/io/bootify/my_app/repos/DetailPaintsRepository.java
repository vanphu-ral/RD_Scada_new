package io.bootify.my_app.repos;

import io.bootify.my_app.domain.DetailPaints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailPaintsRepository extends JpaRepository<DetailPaints, Integer> {

    DetailPaints findFirstByProductionOrderProductionOrderId(Integer productionOrderId);

}
