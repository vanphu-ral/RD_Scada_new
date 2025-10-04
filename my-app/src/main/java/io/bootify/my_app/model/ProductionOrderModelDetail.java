package io.bootify.my_app.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductionOrderModelDetail {
    private ProductionOrderModelsDTO productionOrderModels;
    private MachineGroupDetail machineGroupDetails;
}
