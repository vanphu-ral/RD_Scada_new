package io.bootify.my_app.repos;

import io.bootify.my_app.domain.DetailCover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailCoverRepository extends JpaRepository<DetailCover, Integer> {

    DetailCover findFirstByProductionOrderProductionOrderId(Integer productionOrderId);

}
