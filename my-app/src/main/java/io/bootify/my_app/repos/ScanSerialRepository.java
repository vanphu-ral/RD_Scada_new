package io.bootify.my_app.repos;

import io.bootify.my_app.domain.ScanSerial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScanSerialRepository extends JpaRepository<ScanSerial, Long> {

    ScanSerial findFirstByOrderProductionOrderId(Integer productionOrderId);

    boolean existsBySerialItemIgnoreCase(String serialItem);

}
