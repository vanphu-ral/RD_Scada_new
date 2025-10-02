package io.bootify.my_app.rest;

import io.bootify.my_app.model.DetailLuyenDTO;
import io.bootify.my_app.service.DetailLuyenService;
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
@RequestMapping(value = "/api/detailLuyens", produces = MediaType.APPLICATION_JSON_VALUE)
public class DetailLuyenResource {

    private final DetailLuyenService detailLuyenService;

    public DetailLuyenResource(final DetailLuyenService detailLuyenService) {
        this.detailLuyenService = detailLuyenService;
    }

    @GetMapping
    public ResponseEntity<List<DetailLuyenDTO>> getAllDetailLuyens() {
        return ResponseEntity.ok(detailLuyenService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailLuyenDTO> getDetailLuyen(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(detailLuyenService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDetailLuyen(
            @RequestBody @Valid final DetailLuyenDTO detailLuyenDTO) {
        final Long createdId = detailLuyenService.create(detailLuyenDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDetailLuyen(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final DetailLuyenDTO detailLuyenDTO) {
        detailLuyenService.update(id, detailLuyenDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDetailLuyen(@PathVariable(name = "id") final Long id) {
        detailLuyenService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
