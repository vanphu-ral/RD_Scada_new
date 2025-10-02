package io.bootify.my_app.service;

import io.bootify.my_app.domain.ErrorModel;
import io.bootify.my_app.domain.Hmierror;
import io.bootify.my_app.domain.ProductTypeGroup;
import io.bootify.my_app.events.BeforeDeleteHmierror;
import io.bootify.my_app.events.BeforeDeleteProductTypeGroup;
import io.bootify.my_app.model.ErrorModelDTO;
import io.bootify.my_app.repos.ErrorModelRepository;
import io.bootify.my_app.repos.HmierrorRepository;
import io.bootify.my_app.repos.ProductTypeGroupRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.ReferencedException;
import java.util.List;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ErrorModelService {

    private final ErrorModelRepository errorModelRepository;
    private final HmierrorRepository hmierrorRepository;
    private final ProductTypeGroupRepository productTypeGroupRepository;

    public ErrorModelService(final ErrorModelRepository errorModelRepository,
            final HmierrorRepository hmierrorRepository,
            final ProductTypeGroupRepository productTypeGroupRepository) {
        this.errorModelRepository = errorModelRepository;
        this.hmierrorRepository = hmierrorRepository;
        this.productTypeGroupRepository = productTypeGroupRepository;
    }

    public List<ErrorModelDTO> findAll() {
        final List<ErrorModel> errorModels = errorModelRepository.findAll(Sort.by("errorId"));
        return errorModels.stream()
                .map(errorModel -> mapToDTO(errorModel, new ErrorModelDTO()))
                .toList();
    }

    public ErrorModelDTO get(final Integer errorId) {
        return errorModelRepository.findById(errorId)
                .map(errorModel -> mapToDTO(errorModel, new ErrorModelDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ErrorModelDTO errorModelDTO) {
        final ErrorModel errorModel = new ErrorModel();
        mapToEntity(errorModelDTO, errorModel);
        return errorModelRepository.save(errorModel).getErrorId();
    }

    public void update(final Integer errorId, final ErrorModelDTO errorModelDTO) {
        final ErrorModel errorModel = errorModelRepository.findById(errorId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(errorModelDTO, errorModel);
        errorModelRepository.save(errorModel);
    }

    public void delete(final Integer errorId) {
        final ErrorModel errorModel = errorModelRepository.findById(errorId)
                .orElseThrow(NotFoundException::new);
        errorModelRepository.delete(errorModel);
    }

    private ErrorModelDTO mapToDTO(final ErrorModel errorModel, final ErrorModelDTO errorModelDTO) {
        errorModelDTO.setErrorId(errorModel.getErrorId());
        errorModelDTO.setErrorName(errorModel.getErrorName());
        errorModelDTO.setStageId(errorModel.getStageId());
        errorModelDTO.setHmierr(errorModel.getHmierr() == null ? null : errorModel.getHmierr().getHmierrId());
        errorModelDTO.setProductTypeGroup(errorModel.getProductTypeGroup() == null ? null : errorModel.getProductTypeGroup().getProductTypeGroupId());
        return errorModelDTO;
    }

    private ErrorModel mapToEntity(final ErrorModelDTO errorModelDTO, final ErrorModel errorModel) {
        errorModel.setErrorName(errorModelDTO.getErrorName());
        errorModel.setStageId(errorModelDTO.getStageId());
        final Hmierror hmierr = errorModelDTO.getHmierr() == null ? null : hmierrorRepository.findById(errorModelDTO.getHmierr())
                .orElseThrow(() -> new NotFoundException("hmierr not found"));
        errorModel.setHmierr(hmierr);
        final ProductTypeGroup productTypeGroup = errorModelDTO.getProductTypeGroup() == null ? null : productTypeGroupRepository.findById(errorModelDTO.getProductTypeGroup())
                .orElseThrow(() -> new NotFoundException("productTypeGroup not found"));
        errorModel.setProductTypeGroup(productTypeGroup);
        return errorModel;
    }

    @EventListener(BeforeDeleteHmierror.class)
    public void on(final BeforeDeleteHmierror event) {
        final ReferencedException referencedException = new ReferencedException();
        final ErrorModel hmierrErrorModel = errorModelRepository.findFirstByHmierrHmierrId(event.getHmierrId());
        if (hmierrErrorModel != null) {
            referencedException.setKey("hmierror.errorModel.hmierr.referenced");
            referencedException.addParam(hmierrErrorModel.getErrorId());
            throw referencedException;
        }
    }

    @EventListener(BeforeDeleteProductTypeGroup.class)
    public void on(final BeforeDeleteProductTypeGroup event) {
        final ReferencedException referencedException = new ReferencedException();
        final ErrorModel productTypeGroupErrorModel = errorModelRepository.findFirstByProductTypeGroupProductTypeGroupId(event.getProductTypeGroupId());
        if (productTypeGroupErrorModel != null) {
            referencedException.setKey("productTypeGroup.errorModel.productTypeGroup.referenced");
            referencedException.addParam(productTypeGroupErrorModel.getErrorId());
            throw referencedException;
        }
    }

}
