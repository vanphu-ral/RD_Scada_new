package io.bootify.my_app.service;

import io.bootify.my_app.domain.DetailCover;
import io.bootify.my_app.domain.ProductionOrderModels;
import io.bootify.my_app.events.BeforeDeleteProductionOrderModels;
import io.bootify.my_app.model.DetailCoverDTO;
import io.bootify.my_app.repos.DetailCoverRepository;
import io.bootify.my_app.repos.ProductionOrderModelsRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.ReferencedException;
import java.util.List;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DetailCoverService {

    private final DetailCoverRepository detailCoverRepository;
    private final ProductionOrderModelsRepository productionOrderModelsRepository;

    public DetailCoverService(final DetailCoverRepository detailCoverRepository,
            final ProductionOrderModelsRepository productionOrderModelsRepository) {
        this.detailCoverRepository = detailCoverRepository;
        this.productionOrderModelsRepository = productionOrderModelsRepository;
    }

    public List<DetailCoverDTO> findAll() {
        final List<DetailCover> detailCovers = detailCoverRepository.findAll(Sort.by("coverId"));
        return detailCovers.stream()
                .map(detailCover -> mapToDTO(detailCover, new DetailCoverDTO()))
                .toList();
    }

    public DetailCoverDTO get(final Integer coverId) {
        return detailCoverRepository.findById(coverId)
                .map(detailCover -> mapToDTO(detailCover, new DetailCoverDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final DetailCoverDTO detailCoverDTO) {
        final DetailCover detailCover = new DetailCover();
        mapToEntity(detailCoverDTO, detailCover);
        return detailCoverRepository.save(detailCover).getCoverId();
    }

    public void update(final Integer coverId, final DetailCoverDTO detailCoverDTO) {
        final DetailCover detailCover = detailCoverRepository.findById(coverId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(detailCoverDTO, detailCover);
        detailCoverRepository.save(detailCover);
    }

    public void delete(final Integer coverId) {
        final DetailCover detailCover = detailCoverRepository.findById(coverId)
                .orElseThrow(NotFoundException::new);
        detailCoverRepository.delete(detailCover);
    }

    private DetailCoverDTO mapToDTO(final DetailCover detailCover,
            final DetailCoverDTO detailCoverDTO) {
        detailCoverDTO.setCoverId(detailCover.getCoverId());
        detailCoverDTO.setPv1(detailCover.getPv1());
        detailCoverDTO.setPv2(detailCover.getPv2());
        detailCoverDTO.setPv3(detailCover.getPv3());
        detailCoverDTO.setPv4(detailCover.getPv4());
        detailCoverDTO.setPv5(detailCover.getPv5());
        detailCoverDTO.setPv6(detailCover.getPv6());
        detailCoverDTO.setPv7(detailCover.getPv7());
        detailCoverDTO.setPv8(detailCover.getPv8());
        detailCoverDTO.setPv9(detailCover.getPv9());
        detailCoverDTO.setPv10(detailCover.getPv10());
        detailCoverDTO.setPv11(detailCover.getPv11());
        detailCoverDTO.setPv12(detailCover.getPv12());
        detailCoverDTO.setPv13(detailCover.getPv13());
        detailCoverDTO.setPv14(detailCover.getPv14());
        detailCoverDTO.setPv15(detailCover.getPv15());
        detailCoverDTO.setSv1(detailCover.getSv1());
        detailCoverDTO.setSv2(detailCover.getSv2());
        detailCoverDTO.setSv3(detailCover.getSv3());
        detailCoverDTO.setSv4(detailCover.getSv4());
        detailCoverDTO.setSv5(detailCover.getSv5());
        detailCoverDTO.setSv6(detailCover.getSv6());
        detailCoverDTO.setSv7(detailCover.getSv7());
        detailCoverDTO.setSv8(detailCover.getSv8());
        detailCoverDTO.setSv9(detailCover.getSv9());
        detailCoverDTO.setSv10(detailCover.getSv10());
        detailCoverDTO.setSv11(detailCover.getSv11());
        detailCoverDTO.setSv12(detailCover.getSv12());
        detailCoverDTO.setSv13(detailCover.getSv13());
        detailCoverDTO.setSv14(detailCover.getSv14());
        detailCoverDTO.setSv15(detailCover.getSv15());
        detailCoverDTO.setLengthCover(detailCover.getLengthCover());
        detailCoverDTO.setSpeed(detailCover.getSpeed());
        detailCoverDTO.setWeightUsed(detailCover.getWeightUsed());
        detailCoverDTO.setWeightError(detailCover.getWeightError());
        detailCoverDTO.setU1(detailCover.getU1());
        detailCoverDTO.setU2(detailCover.getU2());
        detailCoverDTO.setU3(detailCover.getU3());
        detailCoverDTO.setI1(detailCover.getI1());
        detailCoverDTO.setI2(detailCover.getI2());
        detailCoverDTO.setI3(detailCover.getI3());
        detailCoverDTO.setCosF1(detailCover.getCosF1());
        detailCoverDTO.setCosF2(detailCover.getCosF2());
        detailCoverDTO.setCosF3(detailCover.getCosF3());
        detailCoverDTO.setP(detailCover.getP());
        detailCoverDTO.setPh(detailCover.getPh());
        detailCoverDTO.setProductionOrder(detailCover.getProductionOrder() == null ? null : detailCover.getProductionOrder().getProductionOrderId());
        return detailCoverDTO;
    }

    private DetailCover mapToEntity(final DetailCoverDTO detailCoverDTO,
            final DetailCover detailCover) {
        detailCover.setPv1(detailCoverDTO.getPv1());
        detailCover.setPv2(detailCoverDTO.getPv2());
        detailCover.setPv3(detailCoverDTO.getPv3());
        detailCover.setPv4(detailCoverDTO.getPv4());
        detailCover.setPv5(detailCoverDTO.getPv5());
        detailCover.setPv6(detailCoverDTO.getPv6());
        detailCover.setPv7(detailCoverDTO.getPv7());
        detailCover.setPv8(detailCoverDTO.getPv8());
        detailCover.setPv9(detailCoverDTO.getPv9());
        detailCover.setPv10(detailCoverDTO.getPv10());
        detailCover.setPv11(detailCoverDTO.getPv11());
        detailCover.setPv12(detailCoverDTO.getPv12());
        detailCover.setPv13(detailCoverDTO.getPv13());
        detailCover.setPv14(detailCoverDTO.getPv14());
        detailCover.setPv15(detailCoverDTO.getPv15());
        detailCover.setSv1(detailCoverDTO.getSv1());
        detailCover.setSv2(detailCoverDTO.getSv2());
        detailCover.setSv3(detailCoverDTO.getSv3());
        detailCover.setSv4(detailCoverDTO.getSv4());
        detailCover.setSv5(detailCoverDTO.getSv5());
        detailCover.setSv6(detailCoverDTO.getSv6());
        detailCover.setSv7(detailCoverDTO.getSv7());
        detailCover.setSv8(detailCoverDTO.getSv8());
        detailCover.setSv9(detailCoverDTO.getSv9());
        detailCover.setSv10(detailCoverDTO.getSv10());
        detailCover.setSv11(detailCoverDTO.getSv11());
        detailCover.setSv12(detailCoverDTO.getSv12());
        detailCover.setSv13(detailCoverDTO.getSv13());
        detailCover.setSv14(detailCoverDTO.getSv14());
        detailCover.setSv15(detailCoverDTO.getSv15());
        detailCover.setLengthCover(detailCoverDTO.getLengthCover());
        detailCover.setSpeed(detailCoverDTO.getSpeed());
        detailCover.setWeightUsed(detailCoverDTO.getWeightUsed());
        detailCover.setWeightError(detailCoverDTO.getWeightError());
        detailCover.setU1(detailCoverDTO.getU1());
        detailCover.setU2(detailCoverDTO.getU2());
        detailCover.setU3(detailCoverDTO.getU3());
        detailCover.setI1(detailCoverDTO.getI1());
        detailCover.setI2(detailCoverDTO.getI2());
        detailCover.setI3(detailCoverDTO.getI3());
        detailCover.setCosF1(detailCoverDTO.getCosF1());
        detailCover.setCosF2(detailCoverDTO.getCosF2());
        detailCover.setCosF3(detailCoverDTO.getCosF3());
        detailCover.setP(detailCoverDTO.getP());
        detailCover.setPh(detailCoverDTO.getPh());
        final ProductionOrderModels productionOrder = detailCoverDTO.getProductionOrder() == null ? null : productionOrderModelsRepository.findById(detailCoverDTO.getProductionOrder())
                .orElseThrow(() -> new NotFoundException("productionOrder not found"));
        detailCover.setProductionOrder(productionOrder);
        return detailCover;
    }

    @EventListener(BeforeDeleteProductionOrderModels.class)
    public void on(final BeforeDeleteProductionOrderModels event) {
        final ReferencedException referencedException = new ReferencedException();
        final DetailCover productionOrderDetailCover = detailCoverRepository.findFirstByProductionOrderProductionOrderId(event.getProductionOrderId());
        if (productionOrderDetailCover != null) {
            referencedException.setKey("productionOrderModels.detailCover.productionOrder.referenced");
            referencedException.addParam(productionOrderDetailCover.getCoverId());
            throw referencedException;
        }
    }

}
