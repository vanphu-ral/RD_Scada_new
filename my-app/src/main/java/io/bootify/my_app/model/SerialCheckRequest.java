package io.bootify.my_app.model;

import jdk.dynalink.linker.LinkerServices;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SerialCheckRequest {
    private String machineName;
    private String workOrder;
    private Integer stage;
    private String serialItems;


}
