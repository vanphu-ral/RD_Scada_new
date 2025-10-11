package io.bootify.my_app.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
@Getter
@Setter
public class InsertMachineResultDTO {
        private List<String> successMachines;
        private Map<String, String> failedMachines;

        // Constructors, Getters, Setters
}
