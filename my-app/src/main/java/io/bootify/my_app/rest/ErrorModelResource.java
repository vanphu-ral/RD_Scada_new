package io.bootify.my_app.rest;

import io.bootify.my_app.model.ErrorModelDTO;
import io.bootify.my_app.service.ErrorModelService;
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
@RequestMapping(value = "/api/errorModels", produces = MediaType.APPLICATION_JSON_VALUE)
public class ErrorModelResource {

    private final ErrorModelService errorModelService;

    public ErrorModelResource(final ErrorModelService errorModelService) {
        this.errorModelService = errorModelService;
    }

    @GetMapping
    public ResponseEntity<List<ErrorModelDTO>> getAllErrorModels() {
        return ResponseEntity.ok(errorModelService.findAll());
    }

    @GetMapping("/{errorId}")
    public ResponseEntity<ErrorModelDTO> getErrorModel(
            @PathVariable(name = "errorId") final Integer errorId) {
        return ResponseEntity.ok(errorModelService.get(errorId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createErrorModel(
            @RequestBody @Valid final ErrorModelDTO errorModelDTO) {
        final Integer createdErrorId = errorModelService.create(errorModelDTO);
        return new ResponseEntity<>(createdErrorId, HttpStatus.CREATED);
    }

    @PutMapping("/{errorId}")
    public ResponseEntity<Integer> updateErrorModel(
            @PathVariable(name = "errorId") final Integer errorId,
            @RequestBody @Valid final ErrorModelDTO errorModelDTO) {
        errorModelService.update(errorId, errorModelDTO);
        return ResponseEntity.ok(errorId);
    }

    @DeleteMapping("/{errorId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteErrorModel(
            @PathVariable(name = "errorId") final Integer errorId) {
        errorModelService.delete(errorId);
        return ResponseEntity.noContent().build();
    }

}
