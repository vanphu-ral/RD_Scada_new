package io.bootify.my_app.rest;

import io.bootify.my_app.model.ParameterModelsDTO;
import io.bootify.my_app.service.ParameterModelsService;
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
@RequestMapping(value = "/api/parameterModelss", produces = MediaType.APPLICATION_JSON_VALUE)
public class ParameterModelsResource {

    private final ParameterModelsService parameterModelsService;

    public ParameterModelsResource(final ParameterModelsService parameterModelsService) {
        this.parameterModelsService = parameterModelsService;
    }

    @GetMapping
    public ResponseEntity<List<ParameterModelsDTO>> getAllParameterModelss() {
        return ResponseEntity.ok(parameterModelsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParameterModelsDTO> getParameterModels(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(parameterModelsService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createParameterModels(
            @RequestBody @Valid final ParameterModelsDTO parameterModelsDTO) {
        final Integer createdId = parameterModelsService.create(parameterModelsDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateParameterModels(
            @PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final ParameterModelsDTO parameterModelsDTO) {
        parameterModelsService.update(id, parameterModelsDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteParameterModels(@PathVariable(name = "id") final Integer id) {
        parameterModelsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
