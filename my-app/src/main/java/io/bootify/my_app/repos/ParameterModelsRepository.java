package io.bootify.my_app.repos;

import io.bootify.my_app.domain.ParameterModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterModelsRepository extends JpaRepository<ParameterModels, Integer> {

    ParameterModels findFirstByProductProductId(Integer productId);

}
