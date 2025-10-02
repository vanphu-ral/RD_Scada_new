package io.bootify.my_app.service;

import io.bootify.my_app.domain.DetailTestLight;
import io.bootify.my_app.domain.ProductionOrderModels;
import io.bootify.my_app.events.BeforeDeleteProductionOrderModels;
import io.bootify.my_app.model.DetailTestLightDTO;
import io.bootify.my_app.repos.DetailTestLightRepository;
import io.bootify.my_app.repos.ProductionOrderModelsRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.ReferencedException;
import java.util.List;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DetailTestLightService {

    private final DetailTestLightRepository detailTestLightRepository;
    private final ProductionOrderModelsRepository productionOrderModelsRepository;

    public DetailTestLightService(final DetailTestLightRepository detailTestLightRepository,
            final ProductionOrderModelsRepository productionOrderModelsRepository) {
        this.detailTestLightRepository = detailTestLightRepository;
        this.productionOrderModelsRepository = productionOrderModelsRepository;
    }

    public List<DetailTestLightDTO> findAll() {
        final List<DetailTestLight> detailTestLights = detailTestLightRepository.findAll(Sort.by("id"));
        return detailTestLights.stream()
                .map(detailTestLight -> mapToDTO(detailTestLight, new DetailTestLightDTO()))
                .toList();
    }

    public DetailTestLightDTO get(final Long id) {
        return detailTestLightRepository.findById(id)
                .map(detailTestLight -> mapToDTO(detailTestLight, new DetailTestLightDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DetailTestLightDTO detailTestLightDTO) {
        final DetailTestLight detailTestLight = new DetailTestLight();
        mapToEntity(detailTestLightDTO, detailTestLight);
        return detailTestLightRepository.save(detailTestLight).getId();
    }

    public void update(final Long id, final DetailTestLightDTO detailTestLightDTO) {
        final DetailTestLight detailTestLight = detailTestLightRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(detailTestLightDTO, detailTestLight);
        detailTestLightRepository.save(detailTestLight);
    }

    public void delete(final Long id) {
        final DetailTestLight detailTestLight = detailTestLightRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        detailTestLightRepository.delete(detailTestLight);
    }

    private DetailTestLightDTO mapToDTO(final DetailTestLight detailTestLight,
            final DetailTestLightDTO detailTestLightDTO) {
        detailTestLightDTO.setId(detailTestLight.getId());
        detailTestLightDTO.setDevicesId(detailTestLight.getDevicesId());
        detailTestLightDTO.setProductId(detailTestLight.getProductId());
        detailTestLightDTO.setTotal(detailTestLight.getTotal());
        detailTestLightDTO.setPass(detailTestLight.getPass());
        detailTestLightDTO.setNg(detailTestLight.getNg());
        detailTestLightDTO.setErrorU(detailTestLight.getErrorU());
        detailTestLightDTO.setErrorI(detailTestLight.getErrorI());
        detailTestLightDTO.setErrorP(detailTestLight.getErrorP());
        detailTestLightDTO.setErrorpF(detailTestLight.getErrorpF());
        detailTestLightDTO.setCh(detailTestLight.getCh());
        detailTestLightDTO.setU(detailTestLight.getU());
        detailTestLightDTO.setI(detailTestLight.getI());
        detailTestLightDTO.setP(detailTestLight.getP());
        detailTestLightDTO.setPF(detailTestLight.getPF());
        detailTestLightDTO.setF(detailTestLight.getF());
        detailTestLightDTO.setResult(detailTestLight.getResult());
        detailTestLightDTO.setStartTime(detailTestLight.getStartTime());
        detailTestLightDTO.setEndTime(detailTestLight.getEndTime());
        detailTestLightDTO.setResult1(detailTestLight.getResult1());
        detailTestLightDTO.setResult2(detailTestLight.getResult2());
        detailTestLightDTO.setProductionOrder(detailTestLight.getProductionOrder() == null ? null : detailTestLight.getProductionOrder().getProductionOrderId());
        return detailTestLightDTO;
    }

    private DetailTestLight mapToEntity(final DetailTestLightDTO detailTestLightDTO,
            final DetailTestLight detailTestLight) {
        detailTestLight.setDevicesId(detailTestLightDTO.getDevicesId());
        detailTestLight.setProductId(detailTestLightDTO.getProductId());
        detailTestLight.setTotal(detailTestLightDTO.getTotal());
        detailTestLight.setPass(detailTestLightDTO.getPass());
        detailTestLight.setNg(detailTestLightDTO.getNg());
        detailTestLight.setErrorU(detailTestLightDTO.getErrorU());
        detailTestLight.setErrorI(detailTestLightDTO.getErrorI());
        detailTestLight.setErrorP(detailTestLightDTO.getErrorP());
        detailTestLight.setErrorpF(detailTestLightDTO.getErrorpF());
        detailTestLight.setCh(detailTestLightDTO.getCh());
        detailTestLight.setU(detailTestLightDTO.getU());
        detailTestLight.setI(detailTestLightDTO.getI());
        detailTestLight.setP(detailTestLightDTO.getP());
        detailTestLight.setPF(detailTestLightDTO.getPF());
        detailTestLight.setF(detailTestLightDTO.getF());
        detailTestLight.setResult(detailTestLightDTO.getResult());
        detailTestLight.setStartTime(detailTestLightDTO.getStartTime());
        detailTestLight.setEndTime(detailTestLightDTO.getEndTime());
        detailTestLight.setResult1(detailTestLightDTO.getResult1());
        detailTestLight.setResult2(detailTestLightDTO.getResult2());
        final ProductionOrderModels productionOrder = detailTestLightDTO.getProductionOrder() == null ? null : productionOrderModelsRepository.findById(detailTestLightDTO.getProductionOrder())
                .orElseThrow(() -> new NotFoundException("productionOrder not found"));
        detailTestLight.setProductionOrder(productionOrder);
        return detailTestLight;
    }

    @EventListener(BeforeDeleteProductionOrderModels.class)
    public void on(final BeforeDeleteProductionOrderModels event) {
        final ReferencedException referencedException = new ReferencedException();
        final DetailTestLight productionOrderDetailTestLight = detailTestLightRepository.findFirstByProductionOrderProductionOrderId(event.getProductionOrderId());
        if (productionOrderDetailTestLight != null) {
            referencedException.setKey("productionOrderModels.detailTestLight.productionOrder.referenced");
            referencedException.addParam(productionOrderDetailTestLight.getId());
            throw referencedException;
        }
    }

}
