package io.bootify.my_app.repos;

import io.bootify.my_app.domain.Hmierror;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HmierrorRepository extends JpaRepository<Hmierror, Integer> {
}
