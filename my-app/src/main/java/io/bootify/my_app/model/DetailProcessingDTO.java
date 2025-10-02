package io.bootify.my_app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DetailProcessingDTO {

    private Long id;

    private Integer machineStatus;

    private Integer machineWorking;

    private Integer numberOfInput;

    private Integer numberOfOutput;

    private Integer runningTime;

    private Integer stoppingTime;

    private Integer cycleTime;

    private Integer e1;

    private Integer e2;

    private Integer e3;

    private Integer e4;

    private Integer e5;

    private Integer e6;

    private Integer e7;

    private Integer e8;

    private Integer e9;

    private Integer e10;

    private Integer e11;

    private Integer e12;

    private Integer e13;

    private Integer e14;

    private Integer e15;

    private Integer e16;

    private Integer e17;

    private Integer e18;

    private Integer e19;

    private Integer e20;

    @Size(max = 20)
    private String hmi;

    private Integer machine;

    private Integer productionOrder;

}
