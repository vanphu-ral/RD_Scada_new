package io.bootify.my_app.repos;

import io.bootify.my_app.domain.ParameterPaints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterPaintsRepository extends JpaRepository<ParameterPaints, Integer> {

    ParameterPaints findFirstByProductProductId(Integer productId);

}
