package io.bootify.my_app.repos;

import io.bootify.my_app.domain.InfoDaq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoDaqRepository extends JpaRepository<InfoDaq, Long> {
}
