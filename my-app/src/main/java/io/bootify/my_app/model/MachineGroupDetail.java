package io.bootify.my_app.model;

import io.bootify.my_app.domain.MachineTypesModels;
import io.bootify.my_app.domain.MachinesModels;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MachineGroupDetail {
    private MachineTypesModelsDTO machineTypesModels;
    private List<MachineDetail> machineDetails;
}
