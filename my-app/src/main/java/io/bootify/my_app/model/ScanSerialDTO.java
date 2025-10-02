package io.bootify.my_app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ScanSerialDTO {

    private Long serialId;

    @Size(max = 50)
    @ScanSerialSerialItemUnique
    private String serialItem;

    @Size(max = 100)
    private String timeUpdate;

    @Size(max = 10)
    private String checked;

    private Integer order;

}
