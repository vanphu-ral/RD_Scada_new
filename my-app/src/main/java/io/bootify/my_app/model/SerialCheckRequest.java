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
    private String detailParams;
    private String timeScan;
    private Integer type; // 1:board , 0:item, 2 : pallet
    private String serialBoard;
    private String status;
    private Integer machineType; //(0: LR, BG , 1: ATE, FCT , 2: Luyện )
}
