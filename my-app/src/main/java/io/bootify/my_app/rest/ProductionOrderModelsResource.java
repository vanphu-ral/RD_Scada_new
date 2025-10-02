package io.bootify.my_app.rest;

import io.bootify.my_app.model.ProductionOrderModelsDTO;
import io.bootify.my_app.service.ProductionOrderModelsService;
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
@RequestMapping(value = "/api/productionOrderModelss", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductionOrderModelsResource {

    private final ProductionOrderModelsService productionOrderModelsService;

    public ProductionOrderModelsResource(
            final ProductionOrderModelsService productionOrderModelsService) {
        this.productionOrderModelsService = productionOrderModelsService;
    }

    @GetMapping
    public ResponseEntity<List<ProductionOrderModelsDTO>> getAllProductionOrderModelss() {
        return ResponseEntity.ok(productionOrderModelsService.findAll());
    }

    @GetMapping("/{productionOrderId}")
    public ResponseEntity<ProductionOrderModelsDTO> getProductionOrderModels(
            @PathVariable(name = "productionOrderId") final Integer productionOrderId) {
        return ResponseEntity.ok(productionOrderModelsService.get(productionOrderId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createProductionOrderModels(
            @RequestBody @Valid final ProductionOrderModelsDTO productionOrderModelsDTO) {
        final Integer createdProductionOrderId = productionOrderModelsService.create(productionOrderModelsDTO);
        return new ResponseEntity<>(createdProductionOrderId, HttpStatus.CREATED);
    }

    @PutMapping("/{productionOrderId}")
    public ResponseEntity<Integer> updateProductionOrderModels(
            @PathVariable(name = "productionOrderId") final Integer productionOrderId,
            @RequestBody @Valid final ProductionOrderModelsDTO productionOrderModelsDTO) {
        productionOrderModelsService.update(productionOrderId, productionOrderModelsDTO);
        return ResponseEntity.ok(productionOrderId);
    }

    @DeleteMapping("/{productionOrderId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteProductionOrderModels(
            @PathVariable(name = "productionOrderId") final Integer productionOrderId) {
        productionOrderModelsService.delete(productionOrderId);
        return ResponseEntity.noContent().build();
    }

}
