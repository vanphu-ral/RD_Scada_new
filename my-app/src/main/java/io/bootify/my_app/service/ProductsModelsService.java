package io.bootify.my_app.service;

import io.bootify.my_app.domain.ProductTypeGroup;
import io.bootify.my_app.domain.ProductsModels;
import io.bootify.my_app.events.BeforeDeleteProductTypeGroup;
import io.bootify.my_app.events.BeforeDeleteProductsModels;
import io.bootify.my_app.model.ProductsModelsDTO;
import io.bootify.my_app.repos.ProductTypeGroupRepository;
import io.bootify.my_app.repos.ProductsModelsRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.ReferencedException;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProductsModelsService {

    private final ProductsModelsRepository productsModelsRepository;
    private final ProductTypeGroupRepository productTypeGroupRepository;
    private final ApplicationEventPublisher publisher;

    public ProductsModelsService(final ProductsModelsRepository productsModelsRepository,
            final ProductTypeGroupRepository productTypeGroupRepository,
            final ApplicationEventPublisher publisher) {
        this.productsModelsRepository = productsModelsRepository;
        this.productTypeGroupRepository = productTypeGroupRepository;
        this.publisher = publisher;
    }

    public List<ProductsModelsDTO> findAll() {
        final List<ProductsModels> productsModelses = productsModelsRepository.findAll(Sort.by("productId"));
        return productsModelses.stream()
                .map(productsModels -> mapToDTO(productsModels, new ProductsModelsDTO()))
                .toList();
    }

    public ProductsModelsDTO get(final Integer productId) {
        return productsModelsRepository.findById(productId)
                .map(productsModels -> mapToDTO(productsModels, new ProductsModelsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ProductsModelsDTO productsModelsDTO) {
        final ProductsModels productsModels = new ProductsModels();
        mapToEntity(productsModelsDTO, productsModels);
        return productsModelsRepository.save(productsModels).getProductId();
    }

    public void update(final Integer productId, final ProductsModelsDTO productsModelsDTO) {
        final ProductsModels productsModels = productsModelsRepository.findById(productId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(productsModelsDTO, productsModels);
        productsModelsRepository.save(productsModels);
    }

    public void delete(final Integer productId) {
        final ProductsModels productsModels = productsModelsRepository.findById(productId)
                .orElseThrow(NotFoundException::new);
        publisher.publishEvent(new BeforeDeleteProductsModels(productId));
        productsModelsRepository.delete(productsModels);
    }

    private ProductsModelsDTO mapToDTO(final ProductsModels productsModels,
            final ProductsModelsDTO productsModelsDTO) {
        productsModelsDTO.setProductId(productsModels.getProductId());
        productsModelsDTO.setProductCode(productsModels.getProductCode());
        productsModelsDTO.setProductName(productsModels.getProductName());
        productsModelsDTO.setDescriptions(productsModels.getDescriptions());
        productsModelsDTO.setProductTypeGroup(productsModels.getProductTypeGroup() == null ? null : productsModels.getProductTypeGroup().getProductTypeGroupId());
        return productsModelsDTO;
    }

    private ProductsModels mapToEntity(final ProductsModelsDTO productsModelsDTO,
            final ProductsModels productsModels) {
        productsModels.setProductCode(productsModelsDTO.getProductCode());
        productsModels.setProductName(productsModelsDTO.getProductName());
        productsModels.setDescriptions(productsModelsDTO.getDescriptions());
        final ProductTypeGroup productTypeGroup = productsModelsDTO.getProductTypeGroup() == null ? null : productTypeGroupRepository.findById(productsModelsDTO.getProductTypeGroup())
                .orElseThrow(() -> new NotFoundException("productTypeGroup not found"));
        productsModels.setProductTypeGroup(productTypeGroup);
        return productsModels;
    }

    @EventListener(BeforeDeleteProductTypeGroup.class)
    public void on(final BeforeDeleteProductTypeGroup event) {
        final ReferencedException referencedException = new ReferencedException();
        final ProductsModels productTypeGroupProductsModels = productsModelsRepository.findFirstByProductTypeGroupProductTypeGroupId(event.getProductTypeGroupId());
        if (productTypeGroupProductsModels != null) {
            referencedException.setKey("productTypeGroup.productsModels.productTypeGroup.referenced");
            referencedException.addParam(productTypeGroupProductsModels.getProductId());
            throw referencedException;
        }
    }

}
