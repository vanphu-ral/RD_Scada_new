package io.bootify.my_app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductTypeGroupDTO {

    private Integer productTypeGroupId;

    @Size(max = 50)
    private String productTypeGroupName;

    private Integer hmiaddress;

    @Size(max = 50)
    private String scadagroup;

}
