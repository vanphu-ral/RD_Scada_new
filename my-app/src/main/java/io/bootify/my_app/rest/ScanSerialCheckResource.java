package io.bootify.my_app.rest;

import io.bootify.my_app.model.ScanSerialCheckDTO;
import io.bootify.my_app.service.ScanSerialCheckService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/scanSerialChecks", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScanSerialCheckResource {

    private final ScanSerialCheckService scanSerialCheckService;

    public ScanSerialCheckResource(final ScanSerialCheckService scanSerialCheckService) {
        this.scanSerialCheckService = scanSerialCheckService;
    }

    @GetMapping
    public ResponseEntity<List<ScanSerialCheckDTO>> getAllScanSerialChecks() {
        return ResponseEntity.ok(scanSerialCheckService.findAll());
    }

    @GetMapping("/{serialId}")
    public ResponseEntity<ScanSerialCheckDTO> getScanSerialCheck(
            @PathVariable(name = "serialId") final Long serialId) {
        return ResponseEntity.ok(scanSerialCheckService.get(serialId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createScanSerialCheck(
            @RequestBody @Valid final ScanSerialCheckDTO scanSerialCheckDTO) {
        final Long createdSerialId = scanSerialCheckService.create(scanSerialCheckDTO);
        return new ResponseEntity<>(createdSerialId, HttpStatus.CREATED);
    }

    @PutMapping("/{serialId}")
    public ResponseEntity<Long> updateScanSerialCheck(
            @PathVariable(name = "serialId") final Long serialId,
            @RequestBody @Valid final ScanSerialCheckDTO scanSerialCheckDTO) {
        scanSerialCheckService.update(serialId, scanSerialCheckDTO);
        return ResponseEntity.ok(serialId);
    }

    @DeleteMapping("/{serialId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteScanSerialCheck(
            @PathVariable(name = "serialId") final Long serialId) {
        scanSerialCheckService.delete(serialId);
        return ResponseEntity.noContent().build();
    }
 @GetMapping("/check")
    public ResponseEntity<List<?>> checkSerials(
         @RequestParam(required = false) String serialItem) {
        return ResponseEntity.ok(scanSerialCheckService.checkSerials(serialItem));
    }
}
