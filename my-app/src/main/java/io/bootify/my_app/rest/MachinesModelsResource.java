package io.bootify.my_app.rest;

import io.bootify.my_app.model.MachinesModelsDTO;
import io.bootify.my_app.service.MachinesModelsService;
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
@RequestMapping(value = "/api/machinesModelss", produces = MediaType.APPLICATION_JSON_VALUE)
public class MachinesModelsResource {

    private final MachinesModelsService machinesModelsService;

    public MachinesModelsResource(final MachinesModelsService machinesModelsService) {
        this.machinesModelsService = machinesModelsService;
    }

    @GetMapping
    public ResponseEntity<List<MachinesModelsDTO>> getAllMachinesModelss() {
        return ResponseEntity.ok(machinesModelsService.findAll());
    }

    @GetMapping("/{machineId}")
    public ResponseEntity<MachinesModelsDTO> getMachinesModels(
            @PathVariable(name = "machineId") final Integer machineId) {
        return ResponseEntity.ok(machinesModelsService.get(machineId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createMachinesModels(
            @RequestBody @Valid final MachinesModelsDTO machinesModelsDTO) {
        final Integer createdMachineId = machinesModelsService.create(machinesModelsDTO);
        return new ResponseEntity<>(createdMachineId, HttpStatus.CREATED);
    }

    @PutMapping("/{machineId}")
    public ResponseEntity<Integer> updateMachinesModels(
            @PathVariable(name = "machineId") final Integer machineId,
            @RequestBody @Valid final MachinesModelsDTO machinesModelsDTO) {
        machinesModelsService.update(machineId, machinesModelsDTO);
        return ResponseEntity.ok(machineId);
    }

    @DeleteMapping("/{machineId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMachinesModels(
            @PathVariable(name = "machineId") final Integer machineId) {
        machinesModelsService.delete(machineId);
        return ResponseEntity.noContent().build();
    }

}
