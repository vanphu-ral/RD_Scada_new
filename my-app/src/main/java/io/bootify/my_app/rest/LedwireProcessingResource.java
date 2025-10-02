package io.bootify.my_app.rest;

import io.bootify.my_app.model.LedwireProcessingDTO;
import io.bootify.my_app.service.LedwireProcessingService;
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
@RequestMapping(value = "/api/ledwireProcessings", produces = MediaType.APPLICATION_JSON_VALUE)
public class LedwireProcessingResource {

    private final LedwireProcessingService ledwireProcessingService;

    public LedwireProcessingResource(final LedwireProcessingService ledwireProcessingService) {
        this.ledwireProcessingService = ledwireProcessingService;
    }

    @GetMapping
    public ResponseEntity<List<LedwireProcessingDTO>> getAllLedwireProcessings() {
        return ResponseEntity.ok(ledwireProcessingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LedwireProcessingDTO> getLedwireProcessing(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(ledwireProcessingService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createLedwireProcessing(
            @RequestBody @Valid final LedwireProcessingDTO ledwireProcessingDTO) {
        final Integer createdId = ledwireProcessingService.create(ledwireProcessingDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateLedwireProcessing(
            @PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final LedwireProcessingDTO ledwireProcessingDTO) {
        ledwireProcessingService.update(id, ledwireProcessingDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLedwireProcessing(
            @PathVariable(name = "id") final Integer id) {
        ledwireProcessingService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
