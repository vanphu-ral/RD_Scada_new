package io.bootify.my_app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LineProductionsModelsDTO {

    private Integer lineId;

    @Size(max = 100)
    private String lineName;

    private String descriptions;

}
