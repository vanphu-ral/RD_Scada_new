package io.bootify.my_app.service;

import io.bootify.my_app.domain.DetailLuyen;
import io.bootify.my_app.domain.ProductionOrderModels;
import io.bootify.my_app.events.BeforeDeleteProductionOrderModels;
import io.bootify.my_app.model.DetailLuyenDTO;
import io.bootify.my_app.repos.DetailLuyenRepository;
import io.bootify.my_app.repos.ProductionOrderModelsRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.ReferencedException;
import java.util.List;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DetailLuyenService {

    private final DetailLuyenRepository detailLuyenRepository;
    private final ProductionOrderModelsRepository productionOrderModelsRepository;

    public DetailLuyenService(final DetailLuyenRepository detailLuyenRepository,
            final ProductionOrderModelsRepository productionOrderModelsRepository) {
        this.detailLuyenRepository = detailLuyenRepository;
        this.productionOrderModelsRepository = productionOrderModelsRepository;
    }

    public List<DetailLuyenDTO> findAll() {
        final List<DetailLuyen> detailLuyens = detailLuyenRepository.findAll(Sort.by("id"));
        return detailLuyens.stream()
                .map(detailLuyen -> mapToDTO(detailLuyen, new DetailLuyenDTO()))
                .toList();
    }

    public DetailLuyenDTO get(final Long id) {
        return detailLuyenRepository.findById(id)
                .map(detailLuyen -> mapToDTO(detailLuyen, new DetailLuyenDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DetailLuyenDTO detailLuyenDTO) {
        final DetailLuyen detailLuyen = new DetailLuyen();
        mapToEntity(detailLuyenDTO, detailLuyen);
        return detailLuyenRepository.save(detailLuyen).getId();
    }

    public void update(final Long id, final DetailLuyenDTO detailLuyenDTO) {
        final DetailLuyen detailLuyen = detailLuyenRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(detailLuyenDTO, detailLuyen);
        detailLuyenRepository.save(detailLuyen);
    }

    public void delete(final Long id) {
        final DetailLuyen detailLuyen = detailLuyenRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        detailLuyenRepository.delete(detailLuyen);
    }

    private DetailLuyenDTO mapToDTO(final DetailLuyen detailLuyen,
            final DetailLuyenDTO detailLuyenDTO) {
        detailLuyenDTO.setId(detailLuyen.getId());
        detailLuyenDTO.setUl1min(detailLuyen.getUl1min());
        detailLuyenDTO.setUl2min(detailLuyen.getUl2min());
        detailLuyenDTO.setUl3min(detailLuyen.getUl3min());
        detailLuyenDTO.setUl4min(detailLuyen.getUl4min());
        detailLuyenDTO.setUl5min(detailLuyen.getUl5min());
        detailLuyenDTO.setUl6min(detailLuyen.getUl6min());
        detailLuyenDTO.setUl7min(detailLuyen.getUl7min());
        detailLuyenDTO.setUl8min(detailLuyen.getUl8min());
        detailLuyenDTO.setUl1max(detailLuyen.getUl1max());
        detailLuyenDTO.setUl2max(detailLuyen.getUl2max());
        detailLuyenDTO.setUl3max(detailLuyen.getUl3max());
        detailLuyenDTO.setUl4max(detailLuyen.getUl4max());
        detailLuyenDTO.setUl5max(detailLuyen.getUl5max());
        detailLuyenDTO.setUl6max(detailLuyen.getUl6max());
        detailLuyenDTO.setUl7max(detailLuyen.getUl7max());
        detailLuyenDTO.setUl8max(detailLuyen.getUl8max());
        detailLuyenDTO.setU1(detailLuyen.getU1());
        detailLuyenDTO.setU2(detailLuyen.getU2());
        detailLuyenDTO.setU3(detailLuyen.getU3());
        detailLuyenDTO.setU4(detailLuyen.getU4());
        detailLuyenDTO.setU5(detailLuyen.getU5());
        detailLuyenDTO.setU6(detailLuyen.getU6());
        detailLuyenDTO.setU7(detailLuyen.getU7());
        detailLuyenDTO.setU8(detailLuyen.getU8());
        detailLuyenDTO.setPv1(detailLuyen.getPv1());
        detailLuyenDTO.setPv2(detailLuyen.getPv2());
        detailLuyenDTO.setPv3(detailLuyen.getPv3());
        detailLuyenDTO.setPv4(detailLuyen.getPv4());
        detailLuyenDTO.setPv5(detailLuyen.getPv5());
        detailLuyenDTO.setPv6(detailLuyen.getPv6());
        detailLuyenDTO.setPv7(detailLuyen.getPv7());
        detailLuyenDTO.setPv8(detailLuyen.getPv8());
        detailLuyenDTO.setSv1(detailLuyen.getSv1());
        detailLuyenDTO.setSv2(detailLuyen.getSv2());
        detailLuyenDTO.setSv3(detailLuyen.getSv3());
        detailLuyenDTO.setSv4(detailLuyen.getSv4());
        detailLuyenDTO.setSv5(detailLuyen.getSv5());
        detailLuyenDTO.setSv6(detailLuyen.getSv6());
        detailLuyenDTO.setSv7(detailLuyen.getSv7());
        detailLuyenDTO.setSv8(detailLuyen.getSv8());
        detailLuyenDTO.setSpeed1(detailLuyen.getSpeed1());
        detailLuyenDTO.setProductionOrder(detailLuyen.getProductionOrder() == null ? null : detailLuyen.getProductionOrder().getProductionOrderId());
        return detailLuyenDTO;
    }

    private DetailLuyen mapToEntity(final DetailLuyenDTO detailLuyenDTO,
            final DetailLuyen detailLuyen) {
        detailLuyen.setUl1min(detailLuyenDTO.getUl1min());
        detailLuyen.setUl2min(detailLuyenDTO.getUl2min());
        detailLuyen.setUl3min(detailLuyenDTO.getUl3min());
        detailLuyen.setUl4min(detailLuyenDTO.getUl4min());
        detailLuyen.setUl5min(detailLuyenDTO.getUl5min());
        detailLuyen.setUl6min(detailLuyenDTO.getUl6min());
        detailLuyen.setUl7min(detailLuyenDTO.getUl7min());
        detailLuyen.setUl8min(detailLuyenDTO.getUl8min());
        detailLuyen.setUl1max(detailLuyenDTO.getUl1max());
        detailLuyen.setUl2max(detailLuyenDTO.getUl2max());
        detailLuyen.setUl3max(detailLuyenDTO.getUl3max());
        detailLuyen.setUl4max(detailLuyenDTO.getUl4max());
        detailLuyen.setUl5max(detailLuyenDTO.getUl5max());
        detailLuyen.setUl6max(detailLuyenDTO.getUl6max());
        detailLuyen.setUl7max(detailLuyenDTO.getUl7max());
        detailLuyen.setUl8max(detailLuyenDTO.getUl8max());
        detailLuyen.setU1(detailLuyenDTO.getU1());
        detailLuyen.setU2(detailLuyenDTO.getU2());
        detailLuyen.setU3(detailLuyenDTO.getU3());
        detailLuyen.setU4(detailLuyenDTO.getU4());
        detailLuyen.setU5(detailLuyenDTO.getU5());
        detailLuyen.setU6(detailLuyenDTO.getU6());
        detailLuyen.setU7(detailLuyenDTO.getU7());
        detailLuyen.setU8(detailLuyenDTO.getU8());
        detailLuyen.setPv1(detailLuyenDTO.getPv1());
        detailLuyen.setPv2(detailLuyenDTO.getPv2());
        detailLuyen.setPv3(detailLuyenDTO.getPv3());
        detailLuyen.setPv4(detailLuyenDTO.getPv4());
        detailLuyen.setPv5(detailLuyenDTO.getPv5());
        detailLuyen.setPv6(detailLuyenDTO.getPv6());
        detailLuyen.setPv7(detailLuyenDTO.getPv7());
        detailLuyen.setPv8(detailLuyenDTO.getPv8());
        detailLuyen.setSv1(detailLuyenDTO.getSv1());
        detailLuyen.setSv2(detailLuyenDTO.getSv2());
        detailLuyen.setSv3(detailLuyenDTO.getSv3());
        detailLuyen.setSv4(detailLuyenDTO.getSv4());
        detailLuyen.setSv5(detailLuyenDTO.getSv5());
        detailLuyen.setSv6(detailLuyenDTO.getSv6());
        detailLuyen.setSv7(detailLuyenDTO.getSv7());
        detailLuyen.setSv8(detailLuyenDTO.getSv8());
        detailLuyen.setSpeed1(detailLuyenDTO.getSpeed1());
        final ProductionOrderModels productionOrder = detailLuyenDTO.getProductionOrder() == null ? null : productionOrderModelsRepository.findById(detailLuyenDTO.getProductionOrder())
                .orElseThrow(() -> new NotFoundException("productionOrder not found"));
        detailLuyen.setProductionOrder(productionOrder);
        return detailLuyen;
    }

    @EventListener(BeforeDeleteProductionOrderModels.class)
    public void on(final BeforeDeleteProductionOrderModels event) {
        final ReferencedException referencedException = new ReferencedException();
        final DetailLuyen productionOrderDetailLuyen = detailLuyenRepository.findFirstByProductionOrderProductionOrderId(event.getProductionOrderId());
        if (productionOrderDetailLuyen != null) {
            referencedException.setKey("productionOrderModels.detailLuyen.productionOrder.referenced");
            referencedException.addParam(productionOrderDetailLuyen.getId());
            throw referencedException;
        }
    }

}
