package io.bootify.my_app.repos;

import io.bootify.my_app.domain.DetailParamsFCTATE;
import io.bootify.my_app.domain.PlanningWO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface DetailParamsFCTATERepository extends JpaRepository<DetailParamsFCTATE, Long> {
    //List<DetailParamsFCTATE> findBySerialID(Long serialID);
    List<DetailParamsFCTATE> findAllByScanSerialCheck_SerialId(Long serialId);

    // Phương thức tìm kiếm theo danh sách IDs (đã dùng trong service)
    List<DetailParamsFCTATE> findAllByScanSerialCheck_SerialIdIn(Collection<Long> serialIds);
}
