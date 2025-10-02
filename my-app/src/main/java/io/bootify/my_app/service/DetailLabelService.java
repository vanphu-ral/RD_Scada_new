package io.bootify.my_app.service;

import io.bootify.my_app.domain.DetailLabel;
import io.bootify.my_app.domain.ProductionOrderModels;
import io.bootify.my_app.events.BeforeDeleteProductionOrderModels;
import io.bootify.my_app.model.DetailLabelDTO;
import io.bootify.my_app.repos.DetailLabelRepository;
import io.bootify.my_app.repos.ProductionOrderModelsRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.ReferencedException;
import java.util.List;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DetailLabelService {

    private final DetailLabelRepository detailLabelRepository;
    private final ProductionOrderModelsRepository productionOrderModelsRepository;

    public DetailLabelService(final DetailLabelRepository detailLabelRepository,
            final ProductionOrderModelsRepository productionOrderModelsRepository) {
        this.detailLabelRepository = detailLabelRepository;
        this.productionOrderModelsRepository = productionOrderModelsRepository;
    }

    public List<DetailLabelDTO> findAll() {
        final List<DetailLabel> detailLabels = detailLabelRepository.findAll(Sort.by("labelId"));
        return detailLabels.stream()
                .map(detailLabel -> mapToDTO(detailLabel, new DetailLabelDTO()))
                .toList();
    }

    public DetailLabelDTO get(final Long labelId) {
        return detailLabelRepository.findById(labelId)
                .map(detailLabel -> mapToDTO(detailLabel, new DetailLabelDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DetailLabelDTO detailLabelDTO) {
        final DetailLabel detailLabel = new DetailLabel();
        mapToEntity(detailLabelDTO, detailLabel);
        return detailLabelRepository.save(detailLabel).getLabelId();
    }

    public void update(final Long labelId, final DetailLabelDTO detailLabelDTO) {
        final DetailLabel detailLabel = detailLabelRepository.findById(labelId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(detailLabelDTO, detailLabel);
        detailLabelRepository.save(detailLabel);
    }

    public void delete(final Long labelId) {
        final DetailLabel detailLabel = detailLabelRepository.findById(labelId)
                .orElseThrow(NotFoundException::new);
        detailLabelRepository.delete(detailLabel);
    }

    private DetailLabelDTO mapToDTO(final DetailLabel detailLabel,
            final DetailLabelDTO detailLabelDTO) {
        detailLabelDTO.setLabelId(detailLabel.getLabelId());
        detailLabelDTO.setQrCode(detailLabel.getQrCode());
        detailLabelDTO.setQuantityTotal(detailLabel.getQuantityTotal());
        detailLabelDTO.setWeightProduct(detailLabel.getWeightProduct());
        detailLabelDTO.setCreatedDate(detailLabel.getCreatedDate());
        detailLabelDTO.setCreatedAt(detailLabel.getCreatedAt());
        detailLabelDTO.setSetWeightProduct(detailLabel.getSetWeightProduct());
        detailLabelDTO.setSetNumberOfPackage(detailLabel.getSetNumberOfPackage());
        detailLabelDTO.setSetTolerances(detailLabel.getSetTolerances());
        detailLabelDTO.setResult(detailLabel.getResult());
        detailLabelDTO.setProductionOrder(detailLabel.getProductionOrder() == null ? null : detailLabel.getProductionOrder().getProductionOrderId());
        return detailLabelDTO;
    }

    private DetailLabel mapToEntity(final DetailLabelDTO detailLabelDTO,
            final DetailLabel detailLabel) {
        detailLabel.setQrCode(detailLabelDTO.getQrCode());
        detailLabel.setQuantityTotal(detailLabelDTO.getQuantityTotal());
        detailLabel.setWeightProduct(detailLabelDTO.getWeightProduct());
        detailLabel.setCreatedDate(detailLabelDTO.getCreatedDate());
        detailLabel.setCreatedAt(detailLabelDTO.getCreatedAt());
        detailLabel.setSetWeightProduct(detailLabelDTO.getSetWeightProduct());
        detailLabel.setSetNumberOfPackage(detailLabelDTO.getSetNumberOfPackage());
        detailLabel.setSetTolerances(detailLabelDTO.getSetTolerances());
        detailLabel.setResult(detailLabelDTO.getResult());
        final ProductionOrderModels productionOrder = detailLabelDTO.getProductionOrder() == null ? null : productionOrderModelsRepository.findById(detailLabelDTO.getProductionOrder())
                .orElseThrow(() -> new NotFoundException("productionOrder not found"));
        detailLabel.setProductionOrder(productionOrder);
        return detailLabel;
    }

    @EventListener(BeforeDeleteProductionOrderModels.class)
    public void on(final BeforeDeleteProductionOrderModels event) {
        final ReferencedException referencedException = new ReferencedException();
        final DetailLabel productionOrderDetailLabel = detailLabelRepository.findFirstByProductionOrderProductionOrderId(event.getProductionOrderId());
        if (productionOrderDetailLabel != null) {
            referencedException.setKey("productionOrderModels.detailLabel.productionOrder.referenced");
            referencedException.addParam(productionOrderDetailLabel.getLabelId());
            throw referencedException;
        }
    }

}
