package io.bootify.my_app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ParameterModelsDTO {

    private Integer id;

    private Integer ch;

    private Double umin;

    private Double umax;

    private Double imin;

    private Double imax;

    private Double pmin;

    private Double pmax;

    private Double cosmin;

    private Double cosmax;

    private Double offsetI;

    private Integer timeCaculator;

    private Integer timeAlert;

    private Double ul1min;

    private Double ul2min;

    private Double ul3min;

    private Double ul4min;

    private Double ul5min;

    private Double ul6min;

    private Double ul7min;

    private Double ul8min;

    private Double ul1max;

    private Double ul2max;

    private Double ul3max;

    private Double ul4max;

    private Double ul5max;

    private Double ul6max;

    private Double ul7max;

    private Double ul8max;

    private Double sv1;

    private Double sv2;

    private Double sv3;

    private Double sv4;

    private Double sv5;

    private Double sv6;

    private Double sv7;

    private Double sv8;

    @Size(max = 50)
    private String productMode;

    private Integer product;

}
