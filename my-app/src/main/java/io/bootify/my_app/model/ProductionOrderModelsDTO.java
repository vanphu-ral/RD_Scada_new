package io.bootify.my_app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductionOrderModelsDTO {

    private Integer productionOrderId;

    @Size(max = 25)
    private String workOrder;

    private Integer working;

    private Integer numberOfPlan;

    @Size(max = 20)
    private String lot;

    private Integer alert1;

    private Integer alert2;

    private Integer alert3;

    private Integer alert4;

    private Integer alert5;

    private Integer alert6;

    private Integer alert7;

    private Integer machineGroup;

    private Integer product;

}
