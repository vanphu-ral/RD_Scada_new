package io.bootify.my_app.repos;

import io.bootify.my_app.domain.DetailQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailQuantityRepository extends JpaRepository<DetailQuantity, Long> {
    List<DetailQuantity> findAllByWorkOrderAndMachineName(String workOrder,String machineName);
    @Query(value = "SELECT SUM(numberOutput) FROM DetailQuantity WHERE workOrder = ?1", nativeQuery = true)
    Integer sumQuantityOutByWorkOrder(String workOrder);
    @Query(value = "SELECT SUM(numberInput) FROM DetailQuantity WHERE workOrder = ?1", nativeQuery = true)
    Integer sumQuantityInByWorkOrder(String workOrder);
}
