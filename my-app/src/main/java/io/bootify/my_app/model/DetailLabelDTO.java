package io.bootify.my_app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DetailLabelDTO {

    private Long labelId;

    @Size(max = 255)
    private String qrCode;

    private Integer quantityTotal;

    private Double weightProduct;

    @Size(max = 50)
    private String createdDate;

    @Size(max = 50)
    private String createdAt;

    private Double setWeightProduct;

    private Double setNumberOfPackage;

    private Double setTolerances;

    @Size(max = 20)
    private String result;

    private Integer productionOrder;

}
