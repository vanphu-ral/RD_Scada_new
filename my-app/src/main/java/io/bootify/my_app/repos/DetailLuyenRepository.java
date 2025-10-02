package io.bootify.my_app.repos;

import io.bootify.my_app.domain.DetailLuyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailLuyenRepository extends JpaRepository<DetailLuyen, Long> {

    DetailLuyen findFirstByProductionOrderProductionOrderId(Integer productionOrderId);

}
