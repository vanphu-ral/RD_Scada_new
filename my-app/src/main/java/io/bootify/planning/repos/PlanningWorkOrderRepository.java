package io.bootify.planning.repos;

import io.bootify.planning.domain.PlanningWorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanningWorkOrderRepository extends JpaRepository<PlanningWorkOrder, String> {

}
