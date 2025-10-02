package io.bootify.my_app.model;

import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DetailQuantityDTO {

    private Long detailQid;

    @Size(max = 20)
    private String workOrder;

    @Size(max = 30)
    private String machineName;

    private Integer numberInput;

    private Integer numberOutput;

    private Integer runTime;

    private Integer stoptime;

    private OffsetDateTime infoDay;

}
