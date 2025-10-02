package io.bootify.my_app.rest;

import io.bootify.my_app.model.UsersModelsDTO;
import io.bootify.my_app.service.UsersModelsService;
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
@RequestMapping(value = "/api/usersModelss", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersModelsResource {

    private final UsersModelsService usersModelsService;

    public UsersModelsResource(final UsersModelsService usersModelsService) {
        this.usersModelsService = usersModelsService;
    }

    @GetMapping
    public ResponseEntity<List<UsersModelsDTO>> getAllUsersModelss() {
        return ResponseEntity.ok(usersModelsService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UsersModelsDTO> getUsersModels(
            @PathVariable(name = "userId") final Integer userId) {
        return ResponseEntity.ok(usersModelsService.get(userId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createUsersModels(
            @RequestBody @Valid final UsersModelsDTO usersModelsDTO) {
        final Integer createdUserId = usersModelsService.create(usersModelsDTO);
        return new ResponseEntity<>(createdUserId, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Integer> updateUsersModels(
            @PathVariable(name = "userId") final Integer userId,
            @RequestBody @Valid final UsersModelsDTO usersModelsDTO) {
        usersModelsService.update(userId, usersModelsDTO);
        return ResponseEntity.ok(userId);
    }

    @DeleteMapping("/{userId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUsersModels(
            @PathVariable(name = "userId") final Integer userId) {
        usersModelsService.delete(userId);
        return ResponseEntity.noContent().build();
    }

}
