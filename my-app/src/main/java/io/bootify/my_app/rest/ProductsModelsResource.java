package io.bootify.my_app.rest;

import io.bootify.my_app.model.ProductsModelsDTO;
import io.bootify.my_app.service.ProductsModelsService;
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
@RequestMapping(value = "/api/productsModelss", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductsModelsResource {

    private final ProductsModelsService productsModelsService;

    public ProductsModelsResource(final ProductsModelsService productsModelsService) {
        this.productsModelsService = productsModelsService;
    }

    @GetMapping
    public ResponseEntity<List<ProductsModelsDTO>> getAllProductsModelss() {
        return ResponseEntity.ok(productsModelsService.findAll());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductsModelsDTO> getProductsModels(
            @PathVariable(name = "productId") final Integer productId) {
        return ResponseEntity.ok(productsModelsService.get(productId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createProductsModels(
            @RequestBody @Valid final ProductsModelsDTO productsModelsDTO) {
        final Integer createdProductId = productsModelsService.create(productsModelsDTO);
        return new ResponseEntity<>(createdProductId, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Integer> updateProductsModels(
            @PathVariable(name = "productId") final Integer productId,
            @RequestBody @Valid final ProductsModelsDTO productsModelsDTO) {
        productsModelsService.update(productId, productsModelsDTO);
        return ResponseEntity.ok(productId);
    }

    @DeleteMapping("/{productId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteProductsModels(
            @PathVariable(name = "productId") final Integer productId) {
        productsModelsService.delete(productId);
        return ResponseEntity.noContent().build();
    }

}
