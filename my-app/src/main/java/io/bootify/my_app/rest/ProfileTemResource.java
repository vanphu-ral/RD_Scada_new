package io.bootify.my_app.rest;

import io.bootify.my_app.model.ProfileTemDTO;
import io.bootify.my_app.service.ProfileTemService;
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
@RequestMapping(value = "/api/profileTems", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileTemResource {

    private final ProfileTemService profileTemService;

    public ProfileTemResource(final ProfileTemService profileTemService) {
        this.profileTemService = profileTemService;
    }

    @GetMapping
    public ResponseEntity<List<ProfileTemDTO>> getAllProfileTems() {
        return ResponseEntity.ok(profileTemService.findAll());
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileTemDTO> getProfileTem(
            @PathVariable(name = "profileId") final Integer profileId) {
        return ResponseEntity.ok(profileTemService.get(profileId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createProfileTem(
            @RequestBody @Valid final ProfileTemDTO profileTemDTO) {
        final Integer createdProfileId = profileTemService.create(profileTemDTO);
        return new ResponseEntity<>(createdProfileId, HttpStatus.CREATED);
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<Integer> updateProfileTem(
            @PathVariable(name = "profileId") final Integer profileId,
            @RequestBody @Valid final ProfileTemDTO profileTemDTO) {
        profileTemService.update(profileId, profileTemDTO);
        return ResponseEntity.ok(profileId);
    }

    @DeleteMapping("/{profileId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteProfileTem(
            @PathVariable(name = "profileId") final Integer profileId) {
        profileTemService.delete(profileId);
        return ResponseEntity.noContent().build();
    }

}
