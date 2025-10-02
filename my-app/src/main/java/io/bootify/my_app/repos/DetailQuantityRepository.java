package io.bootify.my_app.repos;

import io.bootify.my_app.domain.DetailQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailQuantityRepository extends JpaRepository<DetailQuantity, Long> {
}
