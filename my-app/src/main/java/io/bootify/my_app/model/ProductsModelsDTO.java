package io.bootify.my_app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductsModelsDTO {

    private Integer productId;

    @Size(max = 20)
    private String productCode;

    @Size(max = 100)
    private String productName;

    @Size(max = 50)
    private String descriptions;

    private Integer productTypeGroup;

}
