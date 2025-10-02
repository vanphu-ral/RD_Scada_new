package io.bootify.my_app.rest;

import io.bootify.my_app.model.InfoDaqDTO;
import io.bootify.my_app.service.InfoDaqService;
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
@RequestMapping(value = "/api/infoDaqs", produces = MediaType.APPLICATION_JSON_VALUE)
public class InfoDaqResource {

    private final InfoDaqService infoDaqService;

    public InfoDaqResource(final InfoDaqService infoDaqService) {
        this.infoDaqService = infoDaqService;
    }

    @GetMapping
    public ResponseEntity<List<InfoDaqDTO>> getAllInfoDaqs() {
        return ResponseEntity.ok(infoDaqService.findAll());
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<InfoDaqDTO> getInfoDaq(
            @PathVariable(name = "recordId") final Long recordId) {
        return ResponseEntity.ok(infoDaqService.get(recordId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createInfoDaq(@RequestBody @Valid final InfoDaqDTO infoDaqDTO) {
        final Long createdRecordId = infoDaqService.create(infoDaqDTO);
        return new ResponseEntity<>(createdRecordId, HttpStatus.CREATED);
    }

    @PutMapping("/{recordId}")
    public ResponseEntity<Long> updateInfoDaq(@PathVariable(name = "recordId") final Long recordId,
            @RequestBody @Valid final InfoDaqDTO infoDaqDTO) {
        infoDaqService.update(recordId, infoDaqDTO);
        return ResponseEntity.ok(recordId);
    }

    @DeleteMapping("/{recordId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteInfoDaq(
            @PathVariable(name = "recordId") final Long recordId) {
        infoDaqService.delete(recordId);
        return ResponseEntity.noContent().build();
    }

}
