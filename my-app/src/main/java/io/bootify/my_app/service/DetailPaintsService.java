package io.bootify.my_app.service;

import io.bootify.my_app.domain.DetailPaints;
import io.bootify.my_app.domain.ProductionOrderModels;
import io.bootify.my_app.events.BeforeDeleteProductionOrderModels;
import io.bootify.my_app.model.DetailPaintsDTO;
import io.bootify.my_app.repos.DetailPaintsRepository;
import io.bootify.my_app.repos.ProductionOrderModelsRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.ReferencedException;
import java.util.List;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DetailPaintsService {

    private final DetailPaintsRepository detailPaintsRepository;
    private final ProductionOrderModelsRepository productionOrderModelsRepository;

    public DetailPaintsService(final DetailPaintsRepository detailPaintsRepository,
            final ProductionOrderModelsRepository productionOrderModelsRepository) {
        this.detailPaintsRepository = detailPaintsRepository;
        this.productionOrderModelsRepository = productionOrderModelsRepository;
    }

    public List<DetailPaintsDTO> findAll() {
        final List<DetailPaints> detailPaintses = detailPaintsRepository.findAll(Sort.by("detailPaintId"));
        return detailPaintses.stream()
                .map(detailPaints -> mapToDTO(detailPaints, new DetailPaintsDTO()))
                .toList();
    }

    public DetailPaintsDTO get(final Integer detailPaintId) {
        return detailPaintsRepository.findById(detailPaintId)
                .map(detailPaints -> mapToDTO(detailPaints, new DetailPaintsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final DetailPaintsDTO detailPaintsDTO) {
        final DetailPaints detailPaints = new DetailPaints();
        mapToEntity(detailPaintsDTO, detailPaints);
        return detailPaintsRepository.save(detailPaints).getDetailPaintId();
    }

    public void update(final Integer detailPaintId, final DetailPaintsDTO detailPaintsDTO) {
        final DetailPaints detailPaints = detailPaintsRepository.findById(detailPaintId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(detailPaintsDTO, detailPaints);
        detailPaintsRepository.save(detailPaints);
    }

    public void delete(final Integer detailPaintId) {
        final DetailPaints detailPaints = detailPaintsRepository.findById(detailPaintId)
                .orElseThrow(NotFoundException::new);
        detailPaintsRepository.delete(detailPaints);
    }

    private DetailPaintsDTO mapToDTO(final DetailPaints detailPaints,
            final DetailPaintsDTO detailPaintsDTO) {
        detailPaintsDTO.setDetailPaintId(detailPaints.getDetailPaintId());
        detailPaintsDTO.setConveryorSpeed1(detailPaints.getConveryorSpeed1());
        detailPaintsDTO.setConveryorSpeed2(detailPaints.getConveryorSpeed2());
        detailPaintsDTO.setSpeedGun01(detailPaints.getSpeedGun01());
        detailPaintsDTO.setSpeedGun02(detailPaints.getSpeedGun02());
        detailPaintsDTO.setSpeedGun03(detailPaints.getSpeedGun03());
        detailPaintsDTO.setSpeedGun04(detailPaints.getSpeedGun04());
        detailPaintsDTO.setSpeedGun05(detailPaints.getSpeedGun05());
        detailPaintsDTO.setPhArea01(detailPaints.getPhArea01());
        detailPaintsDTO.setPhArea02(detailPaints.getPhArea02());
        detailPaintsDTO.setPhArea03(detailPaints.getPhArea03());
        detailPaintsDTO.setPhArea04(detailPaints.getPhArea04());
        detailPaintsDTO.setPhArea05(detailPaints.getPhArea05());
        detailPaintsDTO.setEsdArea01(detailPaints.getEsdArea01());
        detailPaintsDTO.setEsdArea02(detailPaints.getEsdArea02());
        detailPaintsDTO.setEsdArea03(detailPaints.getEsdArea03());
        detailPaintsDTO.setEsdArea04(detailPaints.getEsdArea04());
        detailPaintsDTO.setEsdArea05(detailPaints.getEsdArea05());
        detailPaintsDTO.setTempArea01(detailPaints.getTempArea01());
        detailPaintsDTO.setTempArea02(detailPaints.getTempArea02());
        detailPaintsDTO.setTempArea03(detailPaints.getTempArea03());
        detailPaintsDTO.setTempArea04(detailPaints.getTempArea04());
        detailPaintsDTO.setTempArea05(detailPaints.getTempArea05());
        detailPaintsDTO.setTempDrying01(detailPaints.getTempDrying01());
        detailPaintsDTO.setTempDrying02(detailPaints.getTempDrying02());
        detailPaintsDTO.setTempDrying03(detailPaints.getTempDrying03());
        detailPaintsDTO.setTempDrying04(detailPaints.getTempDrying04());
        detailPaintsDTO.setTempDrying05(detailPaints.getTempDrying05());
        detailPaintsDTO.setWeightPaintAvaiable(detailPaints.getWeightPaintAvaiable());
        detailPaintsDTO.setWeightPaintUsed(detailPaints.getWeightPaintUsed());
        detailPaintsDTO.setProductionOrder(detailPaints.getProductionOrder() == null ? null : detailPaints.getProductionOrder().getProductionOrderId());
        return detailPaintsDTO;
    }

    private DetailPaints mapToEntity(final DetailPaintsDTO detailPaintsDTO,
            final DetailPaints detailPaints) {
        detailPaints.setConveryorSpeed1(detailPaintsDTO.getConveryorSpeed1());
        detailPaints.setConveryorSpeed2(detailPaintsDTO.getConveryorSpeed2());
        detailPaints.setSpeedGun01(detailPaintsDTO.getSpeedGun01());
        detailPaints.setSpeedGun02(detailPaintsDTO.getSpeedGun02());
        detailPaints.setSpeedGun03(detailPaintsDTO.getSpeedGun03());
        detailPaints.setSpeedGun04(detailPaintsDTO.getSpeedGun04());
        detailPaints.setSpeedGun05(detailPaintsDTO.getSpeedGun05());
        detailPaints.setPhArea01(detailPaintsDTO.getPhArea01());
        detailPaints.setPhArea02(detailPaintsDTO.getPhArea02());
        detailPaints.setPhArea03(detailPaintsDTO.getPhArea03());
        detailPaints.setPhArea04(detailPaintsDTO.getPhArea04());
        detailPaints.setPhArea05(detailPaintsDTO.getPhArea05());
        detailPaints.setEsdArea01(detailPaintsDTO.getEsdArea01());
        detailPaints.setEsdArea02(detailPaintsDTO.getEsdArea02());
        detailPaints.setEsdArea03(detailPaintsDTO.getEsdArea03());
        detailPaints.setEsdArea04(detailPaintsDTO.getEsdArea04());
        detailPaints.setEsdArea05(detailPaintsDTO.getEsdArea05());
        detailPaints.setTempArea01(detailPaintsDTO.getTempArea01());
        detailPaints.setTempArea02(detailPaintsDTO.getTempArea02());
        detailPaints.setTempArea03(detailPaintsDTO.getTempArea03());
        detailPaints.setTempArea04(detailPaintsDTO.getTempArea04());
        detailPaints.setTempArea05(detailPaintsDTO.getTempArea05());
        detailPaints.setTempDrying01(detailPaintsDTO.getTempDrying01());
        detailPaints.setTempDrying02(detailPaintsDTO.getTempDrying02());
        detailPaints.setTempDrying03(detailPaintsDTO.getTempDrying03());
        detailPaints.setTempDrying04(detailPaintsDTO.getTempDrying04());
        detailPaints.setTempDrying05(detailPaintsDTO.getTempDrying05());
        detailPaints.setWeightPaintAvaiable(detailPaintsDTO.getWeightPaintAvaiable());
        detailPaints.setWeightPaintUsed(detailPaintsDTO.getWeightPaintUsed());
        final ProductionOrderModels productionOrder = detailPaintsDTO.getProductionOrder() == null ? null : productionOrderModelsRepository.findById(detailPaintsDTO.getProductionOrder())
                .orElseThrow(() -> new NotFoundException("productionOrder not found"));
        detailPaints.setProductionOrder(productionOrder);
        return detailPaints;
    }

    @EventListener(BeforeDeleteProductionOrderModels.class)
    public void on(final BeforeDeleteProductionOrderModels event) {
        final ReferencedException referencedException = new ReferencedException();
        final DetailPaints productionOrderDetailPaints = detailPaintsRepository.findFirstByProductionOrderProductionOrderId(event.getProductionOrderId());
        if (productionOrderDetailPaints != null) {
            referencedException.setKey("productionOrderModels.detailPaints.productionOrder.referenced");
            referencedException.addParam(productionOrderDetailPaints.getDetailPaintId());
            throw referencedException;
        }
    }

}
