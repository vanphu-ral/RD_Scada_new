package io.bootify.my_app.rest;

import io.bootify.my_app.model.HmierrorDTO;
import io.bootify.my_app.service.HmierrorService;
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
@RequestMapping(value = "/api/hmierrors", produces = MediaType.APPLICATION_JSON_VALUE)
public class HmierrorResource {

    private final HmierrorService hmierrorService;

    public HmierrorResource(final HmierrorService hmierrorService) {
        this.hmierrorService = hmierrorService;
    }

    @GetMapping
    public ResponseEntity<List<HmierrorDTO>> getAllHmierrors() {
        return ResponseEntity.ok(hmierrorService.findAll());
    }

    @GetMapping("/{hmierrId}")
    public ResponseEntity<HmierrorDTO> getHmierror(
            @PathVariable(name = "hmierrId") final Integer hmierrId) {
        return ResponseEntity.ok(hmierrorService.get(hmierrId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createHmierror(
            @RequestBody @Valid final HmierrorDTO hmierrorDTO) {
        final Integer createdHmierrId = hmierrorService.create(hmierrorDTO);
        return new ResponseEntity<>(createdHmierrId, HttpStatus.CREATED);
    }

    @PutMapping("/{hmierrId}")
    public ResponseEntity<Integer> updateHmierror(
            @PathVariable(name = "hmierrId") final Integer hmierrId,
            @RequestBody @Valid final HmierrorDTO hmierrorDTO) {
        hmierrorService.update(hmierrId, hmierrorDTO);
        return ResponseEntity.ok(hmierrId);
    }

    @DeleteMapping("/{hmierrId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteHmierror(
            @PathVariable(name = "hmierrId") final Integer hmierrId) {
        hmierrorService.delete(hmierrId);
        return ResponseEntity.noContent().build();
    }

}
