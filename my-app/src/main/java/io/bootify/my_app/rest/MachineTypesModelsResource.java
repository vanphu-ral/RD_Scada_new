package io.bootify.my_app.rest;

import io.bootify.my_app.model.MachineTypesModelsDTO;
import io.bootify.my_app.service.MachineTypesModelsService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/machineTypesModelss", produces = MediaType.APPLICATION_JSON_VALUE)
public class MachineTypesModelsResource {

    private final MachineTypesModelsService machineTypesModelsService;

    public MachineTypesModelsResource(final MachineTypesModelsService machineTypesModelsService) {
        this.machineTypesModelsService = machineTypesModelsService;
    }

    @GetMapping
    public ResponseEntity<List<MachineTypesModelsDTO>> getAllMachineTypesModelss() {
        return ResponseEntity.ok(machineTypesModelsService.findAll());
    }

    @GetMapping("/{machineGroupId}")
    public ResponseEntity<MachineTypesModelsDTO> getMachineTypesModels(
            @PathVariable(name = "machineGroupId") final Integer machineGroupId) {
        return ResponseEntity.ok(machineTypesModelsService.get(machineGroupId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createMachineTypesModels(
            @RequestBody @Valid final MachineTypesModelsDTO machineTypesModelsDTO) {
        final Integer createdMachineGroupId = machineTypesModelsService.create(machineTypesModelsDTO);
        return new ResponseEntity<>(createdMachineGroupId, HttpStatus.CREATED);
    }

    @PutMapping("/{machineGroupId}")
    public ResponseEntity<Integer> updateMachineTypesModels(
            @PathVariable(name = "machineGroupId") final Integer machineGroupId,
            @RequestBody @Valid final MachineTypesModelsDTO machineTypesModelsDTO) {
        machineTypesModelsService.update(machineGroupId, machineTypesModelsDTO);
        return ResponseEntity.ok(machineGroupId);
    }

    @DeleteMapping("/{machineGroupId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMachineTypesModels(
            @PathVariable(name = "machineGroupId") final Integer machineGroupId) {
        machineTypesModelsService.delete(machineGroupId);
        return ResponseEntity.noContent().build();
    }

}
