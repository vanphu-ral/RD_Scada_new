package io.bootify.my_app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class HmierrorDTO {

    private Integer hmierrId;

    @Size(max = 50)
    private String hmierrCode;

    @Size(max = 50)
    private String hmierrName;

}
