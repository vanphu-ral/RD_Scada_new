package io.bootify.my_app.service;

import io.bootify.my_app.domain.MachinesModels;
import io.bootify.my_app.domain.PlanningWO;
import io.bootify.my_app.domain.ProductionOrderModels;
import io.bootify.my_app.domain.ScanSerialCheck;
import io.bootify.my_app.events.BeforeDeleteMachinesModels;
import io.bootify.my_app.events.BeforeDeleteProductionOrderModels;
import io.bootify.my_app.model.CheckSerialResponse;
import io.bootify.my_app.model.CheckSerialResult;
import io.bootify.my_app.model.ScanSerialCheckDTO;
import io.bootify.my_app.repos.MachinesModelsRepository;
import io.bootify.my_app.repos.PlanningwoRepository;
import io.bootify.my_app.repos.ProductionOrderModelsRepository;
import io.bootify.my_app.repos.ScanSerialCheckRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.ReferencedException;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ScanSerialCheckService {

    private final ScanSerialCheckRepository scanSerialCheckRepository;
    private final MachinesModelsRepository machinesModelsRepository;
    private final ProductionOrderModelsRepository productionOrderModelsRepository;
    private final PlanningwoRepository planningwoRepository;

    public ScanSerialCheckService(final ScanSerialCheckRepository scanSerialCheckRepository,
                                  final MachinesModelsRepository machinesModelsRepository,
                                  final ProductionOrderModelsRepository productionOrderModelsRepository, PlanningwoRepository planningwoRepository) {
        this.scanSerialCheckRepository = scanSerialCheckRepository;
        this.machinesModelsRepository = machinesModelsRepository;
        this.productionOrderModelsRepository = productionOrderModelsRepository;
        this.planningwoRepository = planningwoRepository;
    }

    public List<ScanSerialCheckDTO> findAll() {
        final List<ScanSerialCheck> scanSerialChecks = scanSerialCheckRepository.findAll(Sort.by("serialId"));
        return scanSerialChecks.stream()
                .map(scanSerialCheck -> mapToDTO(scanSerialCheck, new ScanSerialCheckDTO()))
                .toList();
    }

    public ScanSerialCheckDTO get(final Long serialId) {
        return scanSerialCheckRepository.findById(serialId)
                .map(scanSerialCheck -> mapToDTO(scanSerialCheck, new ScanSerialCheckDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ScanSerialCheckDTO scanSerialCheckDTO) {
        final ScanSerialCheck scanSerialCheck = new ScanSerialCheck();
        mapToEntity(scanSerialCheckDTO, scanSerialCheck);
        return scanSerialCheckRepository.save(scanSerialCheck).getSerialId();
    }

    public void update(final Long serialId, final ScanSerialCheckDTO scanSerialCheckDTO) {
        final ScanSerialCheck scanSerialCheck = scanSerialCheckRepository.findById(serialId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(scanSerialCheckDTO, scanSerialCheck);
        scanSerialCheckRepository.save(scanSerialCheck);
    }

    public void delete(final Long serialId) {
        final ScanSerialCheck scanSerialCheck = scanSerialCheckRepository.findById(serialId)
                .orElseThrow(NotFoundException::new);
        scanSerialCheckRepository.delete(scanSerialCheck);
    }
    public CheckSerialResponse checkSerials(String serialItem){
        List<ScanSerialCheck> scanSerialChecks = scanSerialCheckRepository.findAllBySerialItem(serialItem);
        List<CheckSerialResult> list = new ArrayList<>();
        CheckSerialResponse responses = new CheckSerialResponse();
        List<PlanningWO> planningWOS = new ArrayList<>();
        for (ScanSerialCheck s: scanSerialChecks) {
            if (!planningWOS.stream().anyMatch(p -> p.getWoId().equals(s.getWorkOrder()))){
            planningWOS.add(planningwoRepository.findByWoId(s.getWorkOrder()));
            }
           List<CheckSerialResult> checkSerialResult = scanSerialCheckRepository.checkSerials(s.getWorkOrder());
             for (CheckSerialResult c: checkSerialResult) {
                 list.add(c);
             }
        }
        responses.setPlanningWOS(planningWOS);
        responses.setCheckSerialResults(list);
        return responses;
    }
    public ScanSerialCheckDTO mapToDTO(final ScanSerialCheck scanSerialCheck,
            final ScanSerialCheckDTO scanSerialCheckDTO) {
        scanSerialCheckDTO.setSerialId(scanSerialCheck.getSerialId());
        scanSerialCheckDTO.setSerialBoard(scanSerialCheck.getSerialBoard());
        scanSerialCheckDTO.setSerialItem(scanSerialCheck.getSerialItem());
        scanSerialCheckDTO.setSerialStatus(scanSerialCheck.getSerialStatus());
        scanSerialCheckDTO.setSerialCheck(scanSerialCheck.getSerialCheck());
        scanSerialCheckDTO.setTimeScan(scanSerialCheck.getTimeScan());
        scanSerialCheckDTO.setTimeCheck(scanSerialCheck.getTimeCheck());
        scanSerialCheckDTO.setResultCheck(scanSerialCheck.getResultCheck());
        scanSerialCheckDTO.setWorkOrder(scanSerialCheck.getWorkOrder());
        scanSerialCheckDTO.setMachine(scanSerialCheck.getMachine() == null ? null : scanSerialCheck.getMachine().getMachineId());
        scanSerialCheckDTO.setProductionOrder(scanSerialCheck.getProductionOrder() == null ? null : scanSerialCheck.getProductionOrder().getProductionOrderId());
        return scanSerialCheckDTO;
    }

    private ScanSerialCheck mapToEntity(final ScanSerialCheckDTO scanSerialCheckDTO,
            final ScanSerialCheck scanSerialCheck) {
        scanSerialCheck.setSerialBoard(scanSerialCheckDTO.getSerialBoard());
        scanSerialCheck.setSerialItem(scanSerialCheckDTO.getSerialItem());
        scanSerialCheck.setSerialStatus(scanSerialCheckDTO.getSerialStatus());
        scanSerialCheck.setSerialCheck(scanSerialCheckDTO.getSerialCheck());
        scanSerialCheck.setTimeScan(scanSerialCheckDTO.getTimeScan());
        scanSerialCheck.setTimeCheck(scanSerialCheckDTO.getTimeCheck());
        scanSerialCheck.setResultCheck(scanSerialCheckDTO.getResultCheck());
        scanSerialCheck.setWorkOrder(scanSerialCheckDTO.getWorkOrder());
        final MachinesModels machine = scanSerialCheckDTO.getMachine() == null ? null : machinesModelsRepository.findById(scanSerialCheckDTO.getMachine())
                .orElseThrow(() -> new NotFoundException("machine not found"));
        scanSerialCheck.setMachine(machine);
        final ProductionOrderModels productionOrder = scanSerialCheckDTO.getProductionOrder() == null ? null : productionOrderModelsRepository.findById(scanSerialCheckDTO.getProductionOrder())
                .orElseThrow(() -> new NotFoundException("productionOrder not found"));
        scanSerialCheck.setProductionOrder(productionOrder);
        return scanSerialCheck;
    }

    @EventListener(BeforeDeleteMachinesModels.class)
    public void on(final BeforeDeleteMachinesModels event) {
        final ReferencedException referencedException = new ReferencedException();
        final ScanSerialCheck machineScanSerialCheck = scanSerialCheckRepository.findFirstByMachineMachineId(event.getMachineId());
        if (machineScanSerialCheck != null) {
            referencedException.setKey("machinesModels.scanSerialCheck.machine.referenced");
            referencedException.addParam(machineScanSerialCheck.getSerialId());
            throw referencedException;
        }
    }

    @EventListener(BeforeDeleteProductionOrderModels.class)
    public void on(final BeforeDeleteProductionOrderModels event) {
        final ReferencedException referencedException = new ReferencedException();
        final ScanSerialCheck productionOrderScanSerialCheck = scanSerialCheckRepository.findFirstByProductionOrderProductionOrderId(event.getProductionOrderId());
        if (productionOrderScanSerialCheck != null) {
            referencedException.setKey("productionOrderModels.scanSerialCheck.productionOrder.referenced");
            referencedException.addParam(productionOrderScanSerialCheck.getSerialId());
            throw referencedException;
        }
    }

}
