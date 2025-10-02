package io.bootify.my_app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DetailTestLightDTO {

    private Long id;

    @Size(max = 20)
    private String devicesId;

    private Integer productId;

    private Integer total;

    private Integer pass;

    private Integer ng;

    private Integer errorU;

    private Integer errorI;

    private Integer errorP;

    private Integer errorpF;

    private Integer ch;

    private Double u;

    private Double i;

    private Double p;

    @JsonProperty("pF")
    private Double pF;

    private Double f;

    @Size(max = 20)
    private String result;

    private OffsetDateTime startTime;

    private OffsetDateTime endTime;

    @Size(max = 50)
    private String result1;

    @Size(max = 50)
    private String result2;

    private Integer productionOrder;

}
