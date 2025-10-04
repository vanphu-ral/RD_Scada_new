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
    @Query(value = "select " +
            "ErrorID as errorId," +
            "ProductTypeGroupID as productTypeGroupID," +
            "ErrorName as errorName," +
            "StageID as stageID," +
            "HMIErrID as hmiErrID," +
            "(select count(*) from DetailError where workOrder = ?1 and machineName = ?2 and errorName = ErrorName) as quantity " +
            " from ErrorModel where StageID = ?3 ;",nativeQuery = true)
    public List<ErrorResponse> getErrorResponsesByWorkOrderAndMachineNameAndStageID(String workOrder, String machineName, Integer stageID);

}
