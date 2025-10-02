package io.bootify.my_app.service;

import io.bootify.my_app.domain.ProductProfile;
import io.bootify.my_app.domain.ProductsModels;
import io.bootify.my_app.events.BeforeDeleteProductsModels;
import io.bootify.my_app.model.ProductProfileDTO;
import io.bootify.my_app.repos.ProductProfileRepository;
import io.bootify.my_app.repos.ProductsModelsRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.ReferencedException;
import java.util.List;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProductProfileService {

    private final ProductProfileRepository productProfileRepository;
    private final ProductsModelsRepository productsModelsRepository;

    public ProductProfileService(final ProductProfileRepository productProfileRepository,
            final ProductsModelsRepository productsModelsRepository) {
        this.productProfileRepository = productProfileRepository;
        this.productsModelsRepository = productsModelsRepository;
    }

    public List<ProductProfileDTO> findAll() {
        final List<ProductProfile> productProfiles = productProfileRepository.findAll(Sort.by("profileId"));
        return productProfiles.stream()
                .map(productProfile -> mapToDTO(productProfile, new ProductProfileDTO()))
                .toList();
    }

    public ProductProfileDTO get(final Integer profileId) {
        return productProfileRepository.findById(profileId)
                .map(productProfile -> mapToDTO(productProfile, new ProductProfileDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ProductProfileDTO productProfileDTO) {
        final ProductProfile productProfile = new ProductProfile();
        mapToEntity(productProfileDTO, productProfile);
        return productProfileRepository.save(productProfile).getProfileId();
    }

    public void update(final Integer profileId, final ProductProfileDTO productProfileDTO) {
        final ProductProfile productProfile = productProfileRepository.findById(profileId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(productProfileDTO, productProfile);
        productProfileRepository.save(productProfile);
    }

    public void delete(final Integer profileId) {
        final ProductProfile productProfile = productProfileRepository.findById(profileId)
                .orElseThrow(NotFoundException::new);
        productProfileRepository.delete(productProfile);
    }

    private ProductProfileDTO mapToDTO(final ProductProfile productProfile,
            final ProductProfileDTO productProfileDTO) {
        productProfileDTO.setProfileId(productProfile.getProfileId());
        productProfileDTO.setWeightProduct(productProfile.getWeightProduct());
        productProfileDTO.setWeightPackage(productProfile.getWeightPackage());
        productProfileDTO.setNumberOfPackage(productProfile.getNumberOfPackage());
        productProfileDTO.setTolerances(productProfile.getTolerances());
        productProfileDTO.setTimeCalculator(productProfile.getTimeCalculator());
        productProfileDTO.setTimeDelay(productProfile.getTimeDelay());
        productProfileDTO.setProduct(productProfile.getProduct() == null ? null : productProfile.getProduct().getProductId());
        return productProfileDTO;
    }

    private ProductProfile mapToEntity(final ProductProfileDTO productProfileDTO,
            final ProductProfile productProfile) {
        productProfile.setWeightProduct(productProfileDTO.getWeightProduct());
        productProfile.setWeightPackage(productProfileDTO.getWeightPackage());
        productProfile.setNumberOfPackage(productProfileDTO.getNumberOfPackage());
        productProfile.setTolerances(productProfileDTO.getTolerances());
        productProfile.setTimeCalculator(productProfileDTO.getTimeCalculator());
        productProfile.setTimeDelay(productProfileDTO.getTimeDelay());
        final ProductsModels product = productProfileDTO.getProduct() == null ? null : productsModelsRepository.findById(productProfileDTO.getProduct())
                .orElseThrow(() -> new NotFoundException("product not found"));
        productProfile.setProduct(product);
        return productProfile;
    }

    @EventListener(BeforeDeleteProductsModels.class)
    public void on(final BeforeDeleteProductsModels event) {
        final ReferencedException referencedException = new ReferencedException();
        final ProductProfile productProductProfile = productProfileRepository.findFirstByProductProductId(event.getProductId());
        if (productProductProfile != null) {
            referencedException.setKey("productsModels.productProfile.product.referenced");
            referencedException.addParam(productProductProfile.getProfileId());
            throw referencedException;
        }
    }

}
