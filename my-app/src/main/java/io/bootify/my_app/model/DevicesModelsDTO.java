package io.bootify.my_app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DevicesModelsDTO {

    private Integer devicesId;

    @Size(max = 20)
    private String daqName;

    @Size(max = 20)
    private String hmiName;

}
