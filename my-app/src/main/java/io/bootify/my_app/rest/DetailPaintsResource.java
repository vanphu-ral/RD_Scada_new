package io.bootify.my_app.rest;

import io.bootify.my_app.model.DetailPaintsDTO;
import io.bootify.my_app.service.DetailPaintsService;
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
@RequestMapping(value = "/api/detailPaintss", produces = MediaType.APPLICATION_JSON_VALUE)
public class DetailPaintsResource {

    private final DetailPaintsService detailPaintsService;

    public DetailPaintsResource(final DetailPaintsService detailPaintsService) {
        this.detailPaintsService = detailPaintsService;
    }

    @GetMapping
    public ResponseEntity<List<DetailPaintsDTO>> getAllDetailPaintss() {
        return ResponseEntity.ok(detailPaintsService.findAll());
    }

    @GetMapping("/{detailPaintId}")
    public ResponseEntity<DetailPaintsDTO> getDetailPaints(
            @PathVariable(name = "detailPaintId") final Integer detailPaintId) {
        return ResponseEntity.ok(detailPaintsService.get(detailPaintId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createDetailPaints(
            @RequestBody @Valid final DetailPaintsDTO detailPaintsDTO) {
        final Integer createdDetailPaintId = detailPaintsService.create(detailPaintsDTO);
        return new ResponseEntity<>(createdDetailPaintId, HttpStatus.CREATED);
    }

    @PutMapping("/{detailPaintId}")
    public ResponseEntity<Integer> updateDetailPaints(
            @PathVariable(name = "detailPaintId") final Integer detailPaintId,
            @RequestBody @Valid final DetailPaintsDTO detailPaintsDTO) {
        detailPaintsService.update(detailPaintId, detailPaintsDTO);
        return ResponseEntity.ok(detailPaintId);
    }

    @DeleteMapping("/{detailPaintId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDetailPaints(
            @PathVariable(name = "detailPaintId") final Integer detailPaintId) {
        detailPaintsService.delete(detailPaintId);
        return ResponseEntity.noContent().build();
    }

}
