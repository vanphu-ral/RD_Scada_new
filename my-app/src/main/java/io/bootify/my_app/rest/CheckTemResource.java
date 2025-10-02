package io.bootify.my_app.rest;

import io.bootify.my_app.model.CheckTemDTO;
import io.bootify.my_app.service.CheckTemService;
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
@RequestMapping(value = "/api/checkTems", produces = MediaType.APPLICATION_JSON_VALUE)
public class CheckTemResource {

    private final CheckTemService checkTemService;

    public CheckTemResource(final CheckTemService checkTemService) {
        this.checkTemService = checkTemService;
    }

    @GetMapping
    public ResponseEntity<List<CheckTemDTO>> getAllCheckTems() {
        return ResponseEntity.ok(checkTemService.findAll());
    }

    @GetMapping("/{checkId}")
    public ResponseEntity<CheckTemDTO> getCheckTem(
            @PathVariable(name = "checkId") final Long checkId) {
        return ResponseEntity.ok(checkTemService.get(checkId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCheckTem(@RequestBody @Valid final CheckTemDTO checkTemDTO) {
        final Long createdCheckId = checkTemService.create(checkTemDTO);
        return new ResponseEntity<>(createdCheckId, HttpStatus.CREATED);
    }

    @PutMapping("/{checkId}")
    public ResponseEntity<Long> updateCheckTem(@PathVariable(name = "checkId") final Long checkId,
            @RequestBody @Valid final CheckTemDTO checkTemDTO) {
        checkTemService.update(checkId, checkTemDTO);
        return ResponseEntity.ok(checkId);
    }

    @DeleteMapping("/{checkId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCheckTem(@PathVariable(name = "checkId") final Long checkId) {
        checkTemService.delete(checkId);
        return ResponseEntity.noContent().build();
    }

}
