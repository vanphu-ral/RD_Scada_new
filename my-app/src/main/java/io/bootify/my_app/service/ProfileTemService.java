package io.bootify.my_app.service;

import io.bootify.my_app.domain.ProductsModels;
import io.bootify.my_app.domain.ProfileTem;
import io.bootify.my_app.events.BeforeDeleteProductsModels;
import io.bootify.my_app.events.BeforeDeleteProfileTem;
import io.bootify.my_app.model.ProfileTemDTO;
import io.bootify.my_app.repos.ProductsModelsRepository;
import io.bootify.my_app.repos.ProfileTemRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.ReferencedException;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProfileTemService {

    private final ProfileTemRepository profileTemRepository;
    private final ProductsModelsRepository productsModelsRepository;
    private final ApplicationEventPublisher publisher;

    public ProfileTemService(final ProfileTemRepository profileTemRepository,
            final ProductsModelsRepository productsModelsRepository,
            final ApplicationEventPublisher publisher) {
        this.profileTemRepository = profileTemRepository;
        this.productsModelsRepository = productsModelsRepository;
        this.publisher = publisher;
    }

    public List<ProfileTemDTO> findAll() {
        final List<ProfileTem> profileTems = profileTemRepository.findAll(Sort.by("profileId"));
        return profileTems.stream()
                .map(profileTem -> mapToDTO(profileTem, new ProfileTemDTO()))
                .toList();
    }

    public ProfileTemDTO get(final Integer profileId) {
        return profileTemRepository.findById(profileId)
                .map(profileTem -> mapToDTO(profileTem, new ProfileTemDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ProfileTemDTO profileTemDTO) {
        final ProfileTem profileTem = new ProfileTem();
        mapToEntity(profileTemDTO, profileTem);
        return profileTemRepository.save(profileTem).getProfileId();
    }

    public void update(final Integer profileId, final ProfileTemDTO profileTemDTO) {
        final ProfileTem profileTem = profileTemRepository.findById(profileId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(profileTemDTO, profileTem);
        profileTemRepository.save(profileTem);
    }

    public void delete(final Integer profileId) {
        final ProfileTem profileTem = profileTemRepository.findById(profileId)
                .orElseThrow(NotFoundException::new);
        publisher.publishEvent(new BeforeDeleteProfileTem(profileId));
        profileTemRepository.delete(profileTem);
    }

    private ProfileTemDTO mapToDTO(final ProfileTem profileTem, final ProfileTemDTO profileTemDTO) {
        profileTemDTO.setProfileId(profileTem.getProfileId());
        profileTemDTO.setProfileName(profileTem.getProfileName());
        profileTemDTO.setImageUrl(profileTem.getImageUrl());
        profileTemDTO.setRectDetech(profileTem.getRectDetech());
        profileTemDTO.setRectCheck(profileTem.getRectCheck());
        profileTemDTO.setRatio(profileTem.getRatio());
        profileTemDTO.setSizeTem(profileTem.getSizeTem());
        profileTemDTO.setCreatedDate(profileTem.getCreatedDate());
        profileTemDTO.setProduct(profileTem.getProduct() == null ? null : profileTem.getProduct().getProductId());
        return profileTemDTO;
    }

    private ProfileTem mapToEntity(final ProfileTemDTO profileTemDTO, final ProfileTem profileTem) {
        profileTem.setProfileName(profileTemDTO.getProfileName());
        profileTem.setImageUrl(profileTemDTO.getImageUrl());
        profileTem.setRectDetech(profileTemDTO.getRectDetech());
        profileTem.setRectCheck(profileTemDTO.getRectCheck());
        profileTem.setRatio(profileTemDTO.getRatio());
        profileTem.setSizeTem(profileTemDTO.getSizeTem());
        profileTem.setCreatedDate(profileTemDTO.getCreatedDate());
        final ProductsModels product = profileTemDTO.getProduct() == null ? null : productsModelsRepository.findById(profileTemDTO.getProduct())
                .orElseThrow(() -> new NotFoundException("product not found"));
        profileTem.setProduct(product);
        return profileTem;
    }

    @EventListener(BeforeDeleteProductsModels.class)
    public void on(final BeforeDeleteProductsModels event) {
        final ReferencedException referencedException = new ReferencedException();
        final ProfileTem productProfileTem = profileTemRepository.findFirstByProductProductId(event.getProductId());
        if (productProfileTem != null) {
            referencedException.setKey("productsModels.profileTem.product.referenced");
            referencedException.addParam(productProfileTem.getProfileId());
            throw referencedException;
        }
    }

}
