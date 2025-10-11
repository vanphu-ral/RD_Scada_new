package io.bootify.my_app.rest;

import io.bootify.my_app.domain.ErrorCommonScada;
import io.bootify.my_app.domain.PlanningWO;
import io.bootify.my_app.model.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductOrderModelsResponse {
    private PlanningWO planningWO;
    private List<ProductionOrderModelDetail> productionOrderModelDetails;
    private List<ScanSerialChecksResponse> scanSerialChecks;
    private List<ErrorCommonScada> errorCommonScadas;
    private List<MachinesDetailResponse> machinesDetailResponses;
}
