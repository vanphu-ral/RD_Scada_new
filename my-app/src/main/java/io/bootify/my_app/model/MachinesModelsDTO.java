package io.bootify.my_app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MachinesModelsDTO {

    private Integer machineId;

    @Size(max = 25)
    private String machineName;

    private Integer stageId;

    private Integer machineGroup;

    private Integer line;
    private Integer status;
    private Integer lineId;
    private Integer machineGroupId;
    private String workOrder;
}
