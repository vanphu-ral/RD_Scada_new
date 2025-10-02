package io.bootify.my_app.rest;

import io.bootify.my_app.model.DetailCoverDTO;
import io.bootify.my_app.service.DetailCoverService;
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
@RequestMapping(value = "/api/detailCovers", produces = MediaType.APPLICATION_JSON_VALUE)
public class DetailCoverResource {

    private final DetailCoverService detailCoverService;

    public DetailCoverResource(final DetailCoverService detailCoverService) {
        this.detailCoverService = detailCoverService;
    }

    @GetMapping
    public ResponseEntity<List<DetailCoverDTO>> getAllDetailCovers() {
        return ResponseEntity.ok(detailCoverService.findAll());
    }

    @GetMapping("/{coverId}")
    public ResponseEntity<DetailCoverDTO> getDetailCover(
            @PathVariable(name = "coverId") final Integer coverId) {
        return ResponseEntity.ok(detailCoverService.get(coverId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createDetailCover(
            @RequestBody @Valid final DetailCoverDTO detailCoverDTO) {
        final Integer createdCoverId = detailCoverService.create(detailCoverDTO);
        return new ResponseEntity<>(createdCoverId, HttpStatus.CREATED);
    }

    @PutMapping("/{coverId}")
    public ResponseEntity<Integer> updateDetailCover(
            @PathVariable(name = "coverId") final Integer coverId,
            @RequestBody @Valid final DetailCoverDTO detailCoverDTO) {
        detailCoverService.update(coverId, detailCoverDTO);
        return ResponseEntity.ok(coverId);
    }

    @DeleteMapping("/{coverId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDetailCover(
            @PathVariable(name = "coverId") final Integer coverId) {
        detailCoverService.delete(coverId);
        return ResponseEntity.noContent().build();
    }

}
