package io.bootify.my_app.service;

import io.bootify.my_app.domain.ProductTypeGroup;
import io.bootify.my_app.events.BeforeDeleteProductTypeGroup;
import io.bootify.my_app.model.ProductTypeGroupDTO;
import io.bootify.my_app.repos.ProductTypeGroupRepository;
import io.bootify.my_app.util.NotFoundException;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProductTypeGroupService {

    private final ProductTypeGroupRepository productTypeGroupRepository;
    private final ApplicationEventPublisher publisher;

    public ProductTypeGroupService(final ProductTypeGroupRepository productTypeGroupRepository,
            final ApplicationEventPublisher publisher) {
        this.productTypeGroupRepository = productTypeGroupRepository;
        this.publisher = publisher;
    }

    public List<ProductTypeGroupDTO> findAll() {
        final List<ProductTypeGroup> productTypeGroups = productTypeGroupRepository.findAll(Sort.by("productTypeGroupId"));
        return productTypeGroups.stream()
                .map(productTypeGroup -> mapToDTO(productTypeGroup, new ProductTypeGroupDTO()))
                .toList();
    }

    public ProductTypeGroupDTO get(final Integer productTypeGroupId) {
        return productTypeGroupRepository.findById(productTypeGroupId)
                .map(productTypeGroup -> mapToDTO(productTypeGroup, new ProductTypeGroupDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ProductTypeGroupDTO productTypeGroupDTO) {
        final ProductTypeGroup productTypeGroup = new ProductTypeGroup();
        mapToEntity(productTypeGroupDTO, productTypeGroup);
        return productTypeGroupRepository.save(productTypeGroup).getProductTypeGroupId();
    }

    public void update(final Integer productTypeGroupId,
            final ProductTypeGroupDTO productTypeGroupDTO) {
        final ProductTypeGroup productTypeGroup = productTypeGroupRepository.findById(productTypeGroupId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(productTypeGroupDTO, productTypeGroup);
        productTypeGroupRepository.save(productTypeGroup);
    }

    public void delete(final Integer productTypeGroupId) {
        final ProductTypeGroup productTypeGroup = productTypeGroupRepository.findById(productTypeGroupId)
                .orElseThrow(NotFoundException::new);
        publisher.publishEvent(new BeforeDeleteProductTypeGroup(productTypeGroupId));
        productTypeGroupRepository.delete(productTypeGroup);
    }

    private ProductTypeGroupDTO mapToDTO(final ProductTypeGroup productTypeGroup,
            final ProductTypeGroupDTO productTypeGroupDTO) {
        productTypeGroupDTO.setProductTypeGroupId(productTypeGroup.getProductTypeGroupId());
        productTypeGroupDTO.setProductTypeGroupName(productTypeGroup.getProductTypeGroupName());
        productTypeGroupDTO.setHmiaddress(productTypeGroup.getHmiaddress());
        productTypeGroupDTO.setScadagroup(productTypeGroup.getScadagroup());
        return productTypeGroupDTO;
    }

    private ProductTypeGroup mapToEntity(final ProductTypeGroupDTO productTypeGroupDTO,
            final ProductTypeGroup productTypeGroup) {
        productTypeGroup.setProductTypeGroupName(productTypeGroupDTO.getProductTypeGroupName());
        productTypeGroup.setHmiaddress(productTypeGroupDTO.getHmiaddress());
        productTypeGroup.setScadagroup(productTypeGroupDTO.getScadagroup());
        return productTypeGroup;
    }

}
