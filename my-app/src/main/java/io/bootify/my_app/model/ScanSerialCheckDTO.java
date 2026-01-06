package io.bootify.my_app.model;

import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ScanSerialCheckDTO {

    private Long serialId;

    @Size(max = 50)
    private String serialBoard;

    @Size(max = 50)
    private String serialItem;

    @Size(max = 20)
    private String serialStatus;

    @Size(max = 20)
    private String serialCheck;

    private LocalDateTime timeScan;

    private LocalDateTime timeCheck;

    @Size(max = 30)
    private String resultCheck;

    @Size(max = 20)
    private String workOrder;

    private Integer machine;

    private Integer productionOrder;

}
