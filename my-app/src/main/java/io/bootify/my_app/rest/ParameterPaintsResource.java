package io.bootify.my_app.rest;

import io.bootify.my_app.model.ParameterPaintsDTO;
import io.bootify.my_app.service.ParameterPaintsService;
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
@RequestMapping(value = "/api/parameterPaintss", produces = MediaType.APPLICATION_JSON_VALUE)
public class ParameterPaintsResource {

    private final ParameterPaintsService parameterPaintsService;

    public ParameterPaintsResource(final ParameterPaintsService parameterPaintsService) {
        this.parameterPaintsService = parameterPaintsService;
    }

    @GetMapping
    public ResponseEntity<List<ParameterPaintsDTO>> getAllParameterPaintss() {
        return ResponseEntity.ok(parameterPaintsService.findAll());
    }

    @GetMapping("/{idparamsPaint}")
    public ResponseEntity<ParameterPaintsDTO> getParameterPaints(
            @PathVariable(name = "idparamsPaint") final Integer idparamsPaint) {
        return ResponseEntity.ok(parameterPaintsService.get(idparamsPaint));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createParameterPaints(
            @RequestBody @Valid final ParameterPaintsDTO parameterPaintsDTO) {
        final Integer createdIdparamsPaint = parameterPaintsService.create(parameterPaintsDTO);
        return new ResponseEntity<>(createdIdparamsPaint, HttpStatus.CREATED);
    }

    @PutMapping("/{idparamsPaint}")
    public ResponseEntity<Integer> updateParameterPaints(
            @PathVariable(name = "idparamsPaint") final Integer idparamsPaint,
            @RequestBody @Valid final ParameterPaintsDTO parameterPaintsDTO) {
        parameterPaintsService.update(idparamsPaint, parameterPaintsDTO);
        return ResponseEntity.ok(idparamsPaint);
    }

    @DeleteMapping("/{idparamsPaint}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteParameterPaints(
            @PathVariable(name = "idparamsPaint") final Integer idparamsPaint) {
        parameterPaintsService.delete(idparamsPaint);
        return ResponseEntity.noContent().build();
    }

}
