package io.bootify.my_app.rest;

import io.bootify.my_app.model.DetailProcessingDTO;
import io.bootify.my_app.service.DetailProcessingService;
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
@RequestMapping(value = "/api/detailProcessings", produces = MediaType.APPLICATION_JSON_VALUE)
public class DetailProcessingResource {

    private final DetailProcessingService detailProcessingService;

    public DetailProcessingResource(final DetailProcessingService detailProcessingService) {
        this.detailProcessingService = detailProcessingService;
    }

    @GetMapping
    public ResponseEntity<List<DetailProcessingDTO>> getAllDetailProcessings() {
        return ResponseEntity.ok(detailProcessingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailProcessingDTO> getDetailProcessing(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(detailProcessingService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDetailProcessing(
            @RequestBody @Valid final DetailProcessingDTO detailProcessingDTO) {
        final Long createdId = detailProcessingService.create(detailProcessingDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDetailProcessing(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final DetailProcessingDTO detailProcessingDTO) {
        detailProcessingService.update(id, detailProcessingDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDetailProcessing(@PathVariable(name = "id") final Long id) {
        detailProcessingService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
