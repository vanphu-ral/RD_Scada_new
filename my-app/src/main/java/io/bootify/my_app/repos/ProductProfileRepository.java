package io.bootify.my_app.repos;

import io.bootify.my_app.domain.ProductProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductProfileRepository extends JpaRepository<ProductProfile, Integer> {

    ProductProfile findFirstByProductProductId(Integer productId);

}
