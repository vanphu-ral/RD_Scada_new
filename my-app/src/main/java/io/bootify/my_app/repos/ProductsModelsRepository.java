package io.bootify.my_app.repos;

import io.bootify.my_app.domain.ProductsModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsModelsRepository extends JpaRepository<ProductsModels, Integer> {

    ProductsModels findFirstByProductTypeGroupProductTypeGroupId(Integer productTypeGroupId);

}
