package io.bootify.my_app.rest;

import io.bootify.my_app.model.DetailTestLightDTO;
import io.bootify.my_app.service.DetailTestLightService;
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
@RequestMapping(value = "/api/detailTestLights", produces = MediaType.APPLICATION_JSON_VALUE)
public class DetailTestLightResource {

    private final DetailTestLightService detailTestLightService;

    public DetailTestLightResource(final DetailTestLightService detailTestLightService) {
        this.detailTestLightService = detailTestLightService;
    }

    @GetMapping
    public ResponseEntity<List<DetailTestLightDTO>> getAllDetailTestLights() {
        return ResponseEntity.ok(detailTestLightService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailTestLightDTO> getDetailTestLight(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(detailTestLightService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDetailTestLight(
            @RequestBody @Valid final DetailTestLightDTO detailTestLightDTO) {
        final Long createdId = detailTestLightService.create(detailTestLightDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDetailTestLight(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final DetailTestLightDTO detailTestLightDTO) {
        detailTestLightService.update(id, detailTestLightDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDetailTestLight(@PathVariable(name = "id") final Long id) {
        detailTestLightService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
