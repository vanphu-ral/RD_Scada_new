package io.bootify.my_app.repos;

import io.bootify.my_app.domain.ErrorModel;
import io.bootify.my_app.model.ErrorResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ErrorModelRepository extends JpaRepository<ErrorModel, Integer> {

    ErrorModel findFirstByHmierrHmierrId(Integer hmierrId);

    ErrorModel findFirstByProductTypeGroupProductTypeGroupId(Integer productTypeGroupId);
    @Query(value =
            "SELECT  \n" +
            "    e.ErrorID AS errorId, \n" +
            "    e.ProductTypeGroupID AS productTypeGroupID, \n" +
            "    e.ErrorName AS errorName, \n" +
            "    e.StageID AS stageID, \n" +
            "    e.HMIErrID AS hmiErrID, " +
                    "a.err_group as errorGroup, " +
                    "a.json_name as errorCode, \n" +
            "    COUNT(d.detailEID) AS quantity " +
            "FROM \n" +
            "    ErrorModel e\n" +
            "LEFT JOIN \n" +
            "    DetailError d ON d.errorName = e.ErrorName \n" +
            "                 AND d.workOrder = ?1 \n" +
            "                 AND d.machineName = ?2" +
                    " Left join error_common_scada a on a.name = d.errHMI \n" +
            "WHERE \n" +
            "    e.StageID = ?3  \n" +
            "    AND e.ProductTypeGroupID = 36 " +
            "GROUP BY \n" +
            "    e.ErrorID, e.ProductTypeGroupID, e.ErrorName, e.StageID, e.HMIErrID,a.err_group,a.json_name" +
                    " HAVING  \n" +
                    "    COUNT(d.detailEID) > 0 ;",nativeQuery = true)
    public List<ErrorResponse> getErrorResponsesByWorkOrderAndMachineNameAndStageID(String workOrder, String machineName, Integer stageID);

}
