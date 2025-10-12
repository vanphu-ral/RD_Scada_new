package io.bootify.my_app.rest;

import io.bootify.my_app.model.ChatMessage;
import io.bootify.my_app.model.DetailErrorDTO;
import io.bootify.my_app.service.DetailErrorService;
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
@RequestMapping(value = "/api/detailErrors", produces = MediaType.APPLICATION_JSON_VALUE)
public class DetailErrorResource {

    private final DetailErrorService detailErrorService;

    public DetailErrorResource(final DetailErrorService detailErrorService) {
        this.detailErrorService = detailErrorService;
    }

    @GetMapping
    public ResponseEntity<List<DetailErrorDTO>> getAllDetailErrors() {
        return ResponseEntity.ok(detailErrorService.findAll());
    }
    @GetMapping("/history/workOrder/{workOrder}")
    public ResponseEntity<List<ChatMessage>> getAllDetailErrorsByWorkOrder(@PathVariable(name = "workOrder") final String workOrder) {
        return ResponseEntity.ok(detailErrorService.findByWorkOrder(workOrder));
    }
    @PostMapping("/history/update")
    public ResponseEntity<Void> updateDetailErrors(@RequestBody @Valid final ChatMessage message) {
        detailErrorService.updateError(message);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{detailEid}")
    public ResponseEntity<DetailErrorDTO> getDetailError(
            @PathVariable(name = "detailEid") final Long detailEid) {
        return ResponseEntity.ok(detailErrorService.get(detailEid));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDetailError(
            @RequestBody @Valid final DetailErrorDTO detailErrorDTO) {
        final Long createdDetailEid = detailErrorService.create(detailErrorDTO);
        return new ResponseEntity<>(createdDetailEid, HttpStatus.CREATED);
    }

    @PutMapping("/{detailEid}")
    public ResponseEntity<Long> updateDetailError(
            @PathVariable(name = "detailEid") final Long detailEid,
            @RequestBody @Valid final DetailErrorDTO detailErrorDTO) {
        detailErrorService.update(detailEid, detailErrorDTO);
        return ResponseEntity.ok(detailEid);
    }

    @DeleteMapping("/{detailEid}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDetailError(
            @PathVariable(name = "detailEid") final Long detailEid) {
        detailErrorService.delete(detailEid);
        return ResponseEntity.noContent().build();
    }

}
