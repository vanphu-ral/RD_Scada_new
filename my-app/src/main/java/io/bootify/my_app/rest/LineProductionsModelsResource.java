package io.bootify.my_app.rest;

import io.bootify.my_app.model.LineProductionsModelsDTO;
import io.bootify.my_app.service.LineProductionsModelsService;
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
@RequestMapping(value = "/api/lineProductionsModelss", produces = MediaType.APPLICATION_JSON_VALUE)
public class LineProductionsModelsResource {

    private final LineProductionsModelsService lineProductionsModelsService;

    public LineProductionsModelsResource(
            final LineProductionsModelsService lineProductionsModelsService) {
        this.lineProductionsModelsService = lineProductionsModelsService;
    }

    @GetMapping
    public ResponseEntity<List<LineProductionsModelsDTO>> getAllLineProductionsModelss() {
        return ResponseEntity.ok(lineProductionsModelsService.findAll());
    }

    @GetMapping("/{lineId}")
    public ResponseEntity<LineProductionsModelsDTO> getLineProductionsModels(
            @PathVariable(name = "lineId") final Integer lineId) {
        return ResponseEntity.ok(lineProductionsModelsService.get(lineId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createLineProductionsModels(
            @RequestBody @Valid final LineProductionsModelsDTO lineProductionsModelsDTO) {
        final Integer createdLineId = lineProductionsModelsService.create(lineProductionsModelsDTO);
        return new ResponseEntity<>(createdLineId, HttpStatus.CREATED);
    }

    @PutMapping("/{lineId}")
    public ResponseEntity<Integer> updateLineProductionsModels(
            @PathVariable(name = "lineId") final Integer lineId,
            @RequestBody @Valid final LineProductionsModelsDTO lineProductionsModelsDTO) {
        lineProductionsModelsService.update(lineId, lineProductionsModelsDTO);
        return ResponseEntity.ok(lineId);
    }

    @DeleteMapping("/{lineId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLineProductionsModels(
            @PathVariable(name = "lineId") final Integer lineId) {
        lineProductionsModelsService.delete(lineId);
        return ResponseEntity.noContent().build();
    }

}
