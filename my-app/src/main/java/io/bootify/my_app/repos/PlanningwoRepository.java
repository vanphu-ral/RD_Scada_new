package io.bootify.my_app.repos;

import io.bootify.my_app.domain.PlanningWO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanningwoRepository  extends JpaRepository<PlanningWO, Long> {
    Page<PlanningWO> findAll(Specification<PlanningWO> spec, Pageable pageable);
    PlanningWO findFirstByWoId(String workOrder);
}
