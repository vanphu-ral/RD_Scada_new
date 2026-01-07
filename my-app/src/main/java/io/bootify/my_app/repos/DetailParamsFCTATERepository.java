package io.bootify.my_app.repos;

import io.bootify.my_app.domain.DetailParamsFCTATE;
import io.bootify.my_app.domain.PlanningWO;
import io.bootify.my_app.model.DetailParamsFCTATEDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface DetailParamsFCTATERepository extends JpaRepository<DetailParamsFCTATE, Long> {
    //List<DetailParamsFCTATE> findBySerialID(Long serialID);
    List<DetailParamsFCTATE> findAllByScanSerialCheck_SerialId(Long serialId);

    // Phương thức tìm kiếm theo danh sách IDs (đã dùng trong service)
    List<DetailParamsFCTATE> findAllByScanSerialCheck_SerialIdIn(Collection<Long> serialIds);

    @Query("SELECT dp FROM DetailParamsFCTATE dp JOIN dp.scanSerialCheck ssc WHERE ssc.workOrder = :workOrder")
    List<DetailParamsFCTATE> findByWorkOrderEfficient(@Param("workOrder") String workOrder);



    @Query("SELECT new io.bootify.my_app.model.DetailParamsFCTATEDTO(" +
            "  d.paramsID, " +
            "  d.scanSerialCheck.serialId, " +
            "  d.programName, " +
            "  d.machineName, " +
            "  CASE WHEN d.serialBoard = :serial THEN d.serialBoard ELSE d.serialItem END, " +
            "  d.results, " +
            "  d.fixLR, " +
            "  d.fixID, " +
            "  d.startTestTime, " +
            "  d.endTestTime, " +
            "  d.timeElapsed, " +
            "  d.detailParams, " +
            "  d.serialItem, " +
            "  d.workOrder, " +
            "  d.serialBoard, " +
            "  d.machineType " +
            ") " +
            "FROM DetailParamsFCTATE d " +
            "WHERE d.serialBoard = :serial OR d.serialItem = :serial")
    List<DetailParamsFCTATEDTO> findDetailsBySerial(@Param("serial") String serial);


    @Query(value="select top 1 result FROM [ScadaMappingInfo].[dbo].[DetailParamsFCTATE] " +
            "where serialID = ?1 order by paramsID desc ;",nativeQuery = true)
    public String getResultBySerialId(Long serialId);

}
