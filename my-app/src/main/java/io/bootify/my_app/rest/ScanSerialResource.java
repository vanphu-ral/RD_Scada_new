package io.bootify.my_app.rest;

import io.bootify.my_app.model.ScanSerialDTO;
import io.bootify.my_app.service.ScanSerialService;
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
@RequestMapping(value = "/api/scanSerials", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScanSerialResource {

    private final ScanSerialService scanSerialService;

    public ScanSerialResource(final ScanSerialService scanSerialService) {
        this.scanSerialService = scanSerialService;
    }

    @GetMapping
    public ResponseEntity<List<ScanSerialDTO>> getAllScanSerials() {
        return ResponseEntity.ok(scanSerialService.findAll());
    }

    @GetMapping("/{serialId}")
    public ResponseEntity<ScanSerialDTO> getScanSerial(
            @PathVariable(name = "serialId") final Long serialId) {
        return ResponseEntity.ok(scanSerialService.get(serialId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createScanSerial(
            @RequestBody @Valid final ScanSerialDTO scanSerialDTO) {
        final Long createdSerialId = scanSerialService.create(scanSerialDTO);
        return new ResponseEntity<>(createdSerialId, HttpStatus.CREATED);
    }

    @PutMapping("/{serialId}")
    public ResponseEntity<Long> updateScanSerial(
            @PathVariable(name = "serialId") final Long serialId,
            @RequestBody @Valid final ScanSerialDTO scanSerialDTO) {
        scanSerialService.update(serialId, scanSerialDTO);
        return ResponseEntity.ok(serialId);
    }

    @DeleteMapping("/{serialId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteScanSerial(
            @PathVariable(name = "serialId") final Long serialId) {
        scanSerialService.delete(serialId);
        return ResponseEntity.noContent().build();
    }

}
