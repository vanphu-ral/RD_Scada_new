package io.bootify.my_app.rest;

import io.bootify.my_app.model.DetailQuantityDTO;
import io.bootify.my_app.service.DetailQuantityService;
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
@RequestMapping(value = "/api/detailQuantities", produces = MediaType.APPLICATION_JSON_VALUE)
public class DetailQuantityResource {

    private final DetailQuantityService detailQuantityService;

    public DetailQuantityResource(final DetailQuantityService detailQuantityService) {
        this.detailQuantityService = detailQuantityService;
    }

    @GetMapping
    public ResponseEntity<List<DetailQuantityDTO>> getAllDetailQuantities() {
        return ResponseEntity.ok(detailQuantityService.findAll());
    }

    @GetMapping("/{detailQid}")
    public ResponseEntity<DetailQuantityDTO> getDetailQuantity(
            @PathVariable(name = "detailQid") final Long detailQid) {
        return ResponseEntity.ok(detailQuantityService.get(detailQid));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDetailQuantity(
            @RequestBody @Valid final DetailQuantityDTO detailQuantityDTO) {
        final Long createdDetailQid = detailQuantityService.create(detailQuantityDTO);
        return new ResponseEntity<>(createdDetailQid, HttpStatus.CREATED);
    }

    @PutMapping("/{detailQid}")
    public ResponseEntity<Long> updateDetailQuantity(
            @PathVariable(name = "detailQid") final Long detailQid,
            @RequestBody @Valid final DetailQuantityDTO detailQuantityDTO) {
        detailQuantityService.update(detailQid, detailQuantityDTO);
        return ResponseEntity.ok(detailQid);
    }

    @DeleteMapping("/{detailQid}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDetailQuantity(
            @PathVariable(name = "detailQid") final Long detailQid) {
        detailQuantityService.delete(detailQid);
        return ResponseEntity.noContent().build();
    }

}
