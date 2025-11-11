package io.bootify.my_app.model;

import io.bootify.my_app.domain.PlanningWO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CheckSerialResponse {
    private List<PlanningWO> planningWOS;
    private List<CheckSerialResult> checkSerialResults;
}
