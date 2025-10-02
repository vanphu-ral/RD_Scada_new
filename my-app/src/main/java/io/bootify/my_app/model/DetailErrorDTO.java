package io.bootify.my_app.model;

import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DetailErrorDTO {

    private Long detailEid;

    @Size(max = 20)
    private String workOrder;

    @Size(max = 30)
    private String machineName;

    @Size(max = 100)
    private String errorName;

    private OffsetDateTime createdTime;

}
