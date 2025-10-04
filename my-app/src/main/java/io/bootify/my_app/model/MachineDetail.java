package io.bootify.my_app.model;

import io.bootify.my_app.domain.DetailQuantity;
import io.bootify.my_app.domain.ErrorModel;
import io.bootify.my_app.domain.LineProductionsModels;
import io.bootify.my_app.domain.MachinesModels;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MachineDetail {
    private MachinesModels machine;
    private List<ErrorResponse> errors;
    private List<DetailQuantityDTO> detailQuantity;
    private List<ScanSerialCheckDTO> scanSerialChecks;
}
