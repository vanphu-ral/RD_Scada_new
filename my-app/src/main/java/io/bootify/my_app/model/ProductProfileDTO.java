package io.bootify.my_app.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductProfileDTO {

    private Integer profileId;
    private Double weightProduct;
    private Double weightPackage;
    private Double numberOfPackage;
    private Double tolerances;
    private Double timeCalculator;
    private Double timeDelay;
    private Integer product;

}
