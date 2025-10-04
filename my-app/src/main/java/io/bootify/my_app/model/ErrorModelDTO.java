package io.bootify.my_app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ErrorModelDTO {

    private Integer errorId;

    @Size(max = 50)
    private String errorName;

    private Integer stageId;

    private Integer hmierr;

    private Integer productTypeGroup;
    private Integer quantity;

}
