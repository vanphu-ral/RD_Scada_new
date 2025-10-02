package io.bootify.my_app.rest;

import io.bootify.my_app.model.ProductTypeGroupDTO;
import io.bootify.my_app.service.ProductTypeGroupService;
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
@RequestMapping(value = "/api/productTypeGroups", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductTypeGroupResource {

    private final ProductTypeGroupService productTypeGroupService;

    public ProductTypeGroupResource(final ProductTypeGroupService productTypeGroupService) {
        this.productTypeGroupService = productTypeGroupService;
    }

    @GetMapping
    public ResponseEntity<List<ProductTypeGroupDTO>> getAllProductTypeGroups() {
        return ResponseEntity.ok(productTypeGroupService.findAll());
    }

    @GetMapping("/{productTypeGroupId}")
    public ResponseEntity<ProductTypeGroupDTO> getProductTypeGroup(
            @PathVariable(name = "productTypeGroupId") final Integer productTypeGroupId) {
        return ResponseEntity.ok(productTypeGroupService.get(productTypeGroupId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createProductTypeGroup(
            @RequestBody @Valid final ProductTypeGroupDTO productTypeGroupDTO) {
        final Integer createdProductTypeGroupId = productTypeGroupService.create(productTypeGroupDTO);
        return new ResponseEntity<>(createdProductTypeGroupId, HttpStatus.CREATED);
    }

    @PutMapping("/{productTypeGroupId}")
    public ResponseEntity<Integer> updateProductTypeGroup(
            @PathVariable(name = "productTypeGroupId") final Integer productTypeGroupId,
            @RequestBody @Valid final ProductTypeGroupDTO productTypeGroupDTO) {
        productTypeGroupService.update(productTypeGroupId, productTypeGroupDTO);
        return ResponseEntity.ok(productTypeGroupId);
    }

    @DeleteMapping("/{productTypeGroupId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteProductTypeGroup(
            @PathVariable(name = "productTypeGroupId") final Integer productTypeGroupId) {
        productTypeGroupService.delete(productTypeGroupId);
        return ResponseEntity.noContent().build();
    }

}
