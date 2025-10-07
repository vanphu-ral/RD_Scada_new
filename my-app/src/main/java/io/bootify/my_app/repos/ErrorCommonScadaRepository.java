package io.bootify.my_app.repos;

import io.bootify.my_app.domain.ErrorCommonScada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorCommonScadaRepository extends JpaRepository<ErrorCommonScada,Long> {
}
