package io.bootify.my_app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MachineTypesModelsDTO {

    private Integer machineGroupId;

    @Size(max = 25)
    private String machineGroupName;

    @Size(max = 100)
    private String descriptions;

}
