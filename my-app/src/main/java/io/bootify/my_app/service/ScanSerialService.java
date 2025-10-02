package io.bootify.my_app.service;

import io.bootify.my_app.domain.ProductionOrderModels;
import io.bootify.my_app.domain.ScanSerial;
import io.bootify.my_app.events.BeforeDeleteProductionOrderModels;
import io.bootify.my_app.model.ScanSerialDTO;
import io.bootify.my_app.repos.ProductionOrderModelsRepository;
import io.bootify.my_app.repos.ScanSerialRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.ReferencedException;
import java.util.List;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ScanSerialService {

    private final ScanSerialRepository scanSerialRepository;
    private final ProductionOrderModelsRepository productionOrderModelsRepository;

    public ScanSerialService(final ScanSerialRepository scanSerialRepository,
            final ProductionOrderModelsRepository productionOrderModelsRepository) {
        this.scanSerialRepository = scanSerialRepository;
        this.productionOrderModelsRepository = productionOrderModelsRepository;
    }

    public List<ScanSerialDTO> findAll() {
        final List<ScanSerial> scanSerials = scanSerialRepository.findAll(Sort.by("serialId"));
        return scanSerials.stream()
                .map(scanSerial -> mapToDTO(scanSerial, new ScanSerialDTO()))
                .toList();
    }

    public ScanSerialDTO get(final Long serialId) {
        return scanSerialRepository.findById(serialId)
                .map(scanSerial -> mapToDTO(scanSerial, new ScanSerialDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ScanSerialDTO scanSerialDTO) {
        final ScanSerial scanSerial = new ScanSerial();
        mapToEntity(scanSerialDTO, scanSerial);
        return scanSerialRepository.save(scanSerial).getSerialId();
    }

    public void update(final Long serialId, final ScanSerialDTO scanSerialDTO) {
        final ScanSerial scanSerial = scanSerialRepository.findById(serialId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(scanSerialDTO, scanSerial);
        scanSerialRepository.save(scanSerial);
    }

    public void delete(final Long serialId) {
        final ScanSerial scanSerial = scanSerialRepository.findById(serialId)
                .orElseThrow(NotFoundException::new);
        scanSerialRepository.delete(scanSerial);
    }

    private ScanSerialDTO mapToDTO(final ScanSerial scanSerial, final ScanSerialDTO scanSerialDTO) {
        scanSerialDTO.setSerialId(scanSerial.getSerialId());
        scanSerialDTO.setSerialItem(scanSerial.getSerialItem());
        scanSerialDTO.setTimeUpdate(scanSerial.getTimeUpdate());
        scanSerialDTO.setChecked(scanSerial.getChecked());
        scanSerialDTO.setOrder(scanSerial.getOrder() == null ? null : scanSerial.getOrder().getProductionOrderId());
        return scanSerialDTO;
    }

    private ScanSerial mapToEntity(final ScanSerialDTO scanSerialDTO, final ScanSerial scanSerial) {
        scanSerial.setSerialItem(scanSerialDTO.getSerialItem());
        scanSerial.setTimeUpdate(scanSerialDTO.getTimeUpdate());
        scanSerial.setChecked(scanSerialDTO.getChecked());
        final ProductionOrderModels order = scanSerialDTO.getOrder() == null ? null : productionOrderModelsRepository.findById(scanSerialDTO.getOrder())
                .orElseThrow(() -> new NotFoundException("order not found"));
        scanSerial.setOrder(order);
        return scanSerial;
    }

    public boolean serialItemExists(final String serialItem) {
        return scanSerialRepository.existsBySerialItemIgnoreCase(serialItem);
    }

    @EventListener(BeforeDeleteProductionOrderModels.class)
    public void on(final BeforeDeleteProductionOrderModels event) {
        final ReferencedException referencedException = new ReferencedException();
        final ScanSerial orderScanSerial = scanSerialRepository.findFirstByOrderProductionOrderId(event.getProductionOrderId());
        if (orderScanSerial != null) {
            referencedException.setKey("productionOrderModels.scanSerial.order.referenced");
            referencedException.addParam(orderScanSerial.getSerialId());
            throw referencedException;
        }
    }

}
