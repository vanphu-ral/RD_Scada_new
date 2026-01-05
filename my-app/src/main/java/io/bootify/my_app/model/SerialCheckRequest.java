package io.bootify.my_app.model;

import jdk.dynalink.linker.LinkerServices;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SerialCheckRequest {
    private String machineName;
    private String workOrder;
    private Integer stage;
    private String serialItems;
    private String detail;
    private LocalDateTime timeScan;
    private Integer type; // 1:board , 0:item
    private String serialBoard;
    private String serialStatus;
}
