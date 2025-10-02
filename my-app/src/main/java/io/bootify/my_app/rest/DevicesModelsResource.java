package io.bootify.my_app.rest;

import io.bootify.my_app.model.DevicesModelsDTO;
import io.bootify.my_app.service.DevicesModelsService;
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
@RequestMapping(value = "/api/devicesModelss", produces = MediaType.APPLICATION_JSON_VALUE)
public class DevicesModelsResource {

    private final DevicesModelsService devicesModelsService;

    public DevicesModelsResource(final DevicesModelsService devicesModelsService) {
        this.devicesModelsService = devicesModelsService;
    }

    @GetMapping
    public ResponseEntity<List<DevicesModelsDTO>> getAllDevicesModelss() {
        return ResponseEntity.ok(devicesModelsService.findAll());
    }

    @GetMapping("/{devicesId}")
    public ResponseEntity<DevicesModelsDTO> getDevicesModels(
            @PathVariable(name = "devicesId") final Integer devicesId) {
        return ResponseEntity.ok(devicesModelsService.get(devicesId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createDevicesModels(
            @RequestBody @Valid final DevicesModelsDTO devicesModelsDTO) {
        final Integer createdDevicesId = devicesModelsService.create(devicesModelsDTO);
        return new ResponseEntity<>(createdDevicesId, HttpStatus.CREATED);
    }

    @PutMapping("/{devicesId}")
    public ResponseEntity<Integer> updateDevicesModels(
            @PathVariable(name = "devicesId") final Integer devicesId,
            @RequestBody @Valid final DevicesModelsDTO devicesModelsDTO) {
        devicesModelsService.update(devicesId, devicesModelsDTO);
        return ResponseEntity.ok(devicesId);
    }

    @DeleteMapping("/{devicesId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDevicesModels(
            @PathVariable(name = "devicesId") final Integer devicesId) {
        devicesModelsService.delete(devicesId);
        return ResponseEntity.noContent().build();
    }

}
