package io.bootify.my_app.rest;

import io.bootify.my_app.model.ProductProfileDTO;
import io.bootify.my_app.service.ProductProfileService;
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
@RequestMapping(value = "/api/productProfiles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductProfileResource {

    private final ProductProfileService productProfileService;

    public ProductProfileResource(final ProductProfileService productProfileService) {
        this.productProfileService = productProfileService;
    }

    @GetMapping
    public ResponseEntity<List<ProductProfileDTO>> getAllProductProfiles() {
        return ResponseEntity.ok(productProfileService.findAll());
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<ProductProfileDTO> getProductProfile(
            @PathVariable(name = "profileId") final Integer profileId) {
        return ResponseEntity.ok(productProfileService.get(profileId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createProductProfile(
            @RequestBody @Valid final ProductProfileDTO productProfileDTO) {
        final Integer createdProfileId = productProfileService.create(productProfileDTO);
        return new ResponseEntity<>(createdProfileId, HttpStatus.CREATED);
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<Integer> updateProductProfile(
            @PathVariable(name = "profileId") final Integer profileId,
            @RequestBody @Valid final ProductProfileDTO productProfileDTO) {
        productProfileService.update(profileId, productProfileDTO);
        return ResponseEntity.ok(profileId);
    }

    @DeleteMapping("/{profileId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteProductProfile(
            @PathVariable(name = "profileId") final Integer profileId) {
        productProfileService.delete(profileId);
        return ResponseEntity.noContent().build();
    }

}
