package io.bootify.my_app.rest;

import io.bootify.my_app.domain.PlanningWO;
import io.bootify.my_app.model.MachineGroupDetail;
import io.bootify.my_app.model.ProductionOrderModelDetail;
import io.bootify.my_app.model.ProductionOrderModelsDTO;
import io.bootify.my_app.model.ScanSerialCheckDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductOrderModelsResponse {
    private PlanningWO planningWO;
    private List<ProductionOrderModelDetail> productionOrderModelDetails;
    private List<ScanSerialCheckDTO> scanSerialChecks;
}
