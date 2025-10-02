package io.bootify.my_app.service;

import io.bootify.my_app.domain.DetailProcessing;
import io.bootify.my_app.domain.MachinesModels;
import io.bootify.my_app.domain.ProductionOrderModels;
import io.bootify.my_app.events.BeforeDeleteMachinesModels;
import io.bootify.my_app.events.BeforeDeleteProductionOrderModels;
import io.bootify.my_app.model.DetailProcessingDTO;
import io.bootify.my_app.repos.DetailProcessingRepository;
import io.bootify.my_app.repos.MachinesModelsRepository;
import io.bootify.my_app.repos.ProductionOrderModelsRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.ReferencedException;
import java.util.List;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DetailProcessingService {

    private final DetailProcessingRepository detailProcessingRepository;
    private final MachinesModelsRepository machinesModelsRepository;
    private final ProductionOrderModelsRepository productionOrderModelsRepository;

    public DetailProcessingService(final DetailProcessingRepository detailProcessingRepository,
            final MachinesModelsRepository machinesModelsRepository,
            final ProductionOrderModelsRepository productionOrderModelsRepository) {
        this.detailProcessingRepository = detailProcessingRepository;
        this.machinesModelsRepository = machinesModelsRepository;
        this.productionOrderModelsRepository = productionOrderModelsRepository;
    }

    public List<DetailProcessingDTO> findAll() {
        final List<DetailProcessing> detailProcessings = detailProcessingRepository.findAll(Sort.by("id"));
        return detailProcessings.stream()
                .map(detailProcessing -> mapToDTO(detailProcessing, new DetailProcessingDTO()))
                .toList();
    }

    public DetailProcessingDTO get(final Long id) {
        return detailProcessingRepository.findById(id)
                .map(detailProcessing -> mapToDTO(detailProcessing, new DetailProcessingDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DetailProcessingDTO detailProcessingDTO) {
        final DetailProcessing detailProcessing = new DetailProcessing();
        mapToEntity(detailProcessingDTO, detailProcessing);
        return detailProcessingRepository.save(detailProcessing).getId();
    }

    public void update(final Long id, final DetailProcessingDTO detailProcessingDTO) {
        final DetailProcessing detailProcessing = detailProcessingRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(detailProcessingDTO, detailProcessing);
        detailProcessingRepository.save(detailProcessing);
    }

    public void delete(final Long id) {
        final DetailProcessing detailProcessing = detailProcessingRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        detailProcessingRepository.delete(detailProcessing);
    }

    private DetailProcessingDTO mapToDTO(final DetailProcessing detailProcessing,
            final DetailProcessingDTO detailProcessingDTO) {
        detailProcessingDTO.setId(detailProcessing.getId());
        detailProcessingDTO.setMachineStatus(detailProcessing.getMachineStatus());
        detailProcessingDTO.setMachineWorking(detailProcessing.getMachineWorking());
        detailProcessingDTO.setNumberOfInput(detailProcessing.getNumberOfInput());
        detailProcessingDTO.setNumberOfOutput(detailProcessing.getNumberOfOutput());
        detailProcessingDTO.setRunningTime(detailProcessing.getRunningTime());
        detailProcessingDTO.setStoppingTime(detailProcessing.getStoppingTime());
        detailProcessingDTO.setCycleTime(detailProcessing.getCycleTime());
        detailProcessingDTO.setE1(detailProcessing.getE1());
        detailProcessingDTO.setE2(detailProcessing.getE2());
        detailProcessingDTO.setE3(detailProcessing.getE3());
        detailProcessingDTO.setE4(detailProcessing.getE4());
        detailProcessingDTO.setE5(detailProcessing.getE5());
        detailProcessingDTO.setE6(detailProcessing.getE6());
        detailProcessingDTO.setE7(detailProcessing.getE7());
        detailProcessingDTO.setE8(detailProcessing.getE8());
        detailProcessingDTO.setE9(detailProcessing.getE9());
        detailProcessingDTO.setE10(detailProcessing.getE10());
        detailProcessingDTO.setE11(detailProcessing.getE11());
        detailProcessingDTO.setE12(detailProcessing.getE12());
        detailProcessingDTO.setE13(detailProcessing.getE13());
        detailProcessingDTO.setE14(detailProcessing.getE14());
        detailProcessingDTO.setE15(detailProcessing.getE15());
        detailProcessingDTO.setE16(detailProcessing.getE16());
        detailProcessingDTO.setE17(detailProcessing.getE17());
        detailProcessingDTO.setE18(detailProcessing.getE18());
        detailProcessingDTO.setE19(detailProcessing.getE19());
        detailProcessingDTO.setE20(detailProcessing.getE20());
        detailProcessingDTO.setHmi(detailProcessing.getHmi());
        detailProcessingDTO.setMachine(detailProcessing.getMachine() == null ? null : detailProcessing.getMachine().getMachineId());
        detailProcessingDTO.setProductionOrder(detailProcessing.getProductionOrder() == null ? null : detailProcessing.getProductionOrder().getProductionOrderId());
        return detailProcessingDTO;
    }

    private DetailProcessing mapToEntity(final DetailProcessingDTO detailProcessingDTO,
            final DetailProcessing detailProcessing) {
        detailProcessing.setMachineStatus(detailProcessingDTO.getMachineStatus());
        detailProcessing.setMachineWorking(detailProcessingDTO.getMachineWorking());
        detailProcessing.setNumberOfInput(detailProcessingDTO.getNumberOfInput());
        detailProcessing.setNumberOfOutput(detailProcessingDTO.getNumberOfOutput());
        detailProcessing.setRunningTime(detailProcessingDTO.getRunningTime());
        detailProcessing.setStoppingTime(detailProcessingDTO.getStoppingTime());
        detailProcessing.setCycleTime(detailProcessingDTO.getCycleTime());
        detailProcessing.setE1(detailProcessingDTO.getE1());
        detailProcessing.setE2(detailProcessingDTO.getE2());
        detailProcessing.setE3(detailProcessingDTO.getE3());
        detailProcessing.setE4(detailProcessingDTO.getE4());
        detailProcessing.setE5(detailProcessingDTO.getE5());
        detailProcessing.setE6(detailProcessingDTO.getE6());
        detailProcessing.setE7(detailProcessingDTO.getE7());
        detailProcessing.setE8(detailProcessingDTO.getE8());
        detailProcessing.setE9(detailProcessingDTO.getE9());
        detailProcessing.setE10(detailProcessingDTO.getE10());
        detailProcessing.setE11(detailProcessingDTO.getE11());
        detailProcessing.setE12(detailProcessingDTO.getE12());
        detailProcessing.setE13(detailProcessingDTO.getE13());
        detailProcessing.setE14(detailProcessingDTO.getE14());
        detailProcessing.setE15(detailProcessingDTO.getE15());
        detailProcessing.setE16(detailProcessingDTO.getE16());
        detailProcessing.setE17(detailProcessingDTO.getE17());
        detailProcessing.setE18(detailProcessingDTO.getE18());
        detailProcessing.setE19(detailProcessingDTO.getE19());
        detailProcessing.setE20(detailProcessingDTO.getE20());
        detailProcessing.setHmi(detailProcessingDTO.getHmi());
        final MachinesModels machine = detailProcessingDTO.getMachine() == null ? null : machinesModelsRepository.findById(detailProcessingDTO.getMachine())
                .orElseThrow(() -> new NotFoundException("machine not found"));
        detailProcessing.setMachine(machine);
        final ProductionOrderModels productionOrder = detailProcessingDTO.getProductionOrder() == null ? null : productionOrderModelsRepository.findById(detailProcessingDTO.getProductionOrder())
                .orElseThrow(() -> new NotFoundException("productionOrder not found"));
        detailProcessing.setProductionOrder(productionOrder);
        return detailProcessing;
    }

    @EventListener(BeforeDeleteMachinesModels.class)
    public void on(final BeforeDeleteMachinesModels event) {
        final ReferencedException referencedException = new ReferencedException();
        final DetailProcessing machineDetailProcessing = detailProcessingRepository.findFirstByMachineMachineId(event.getMachineId());
        if (machineDetailProcessing != null) {
            referencedException.setKey("machinesModels.detailProcessing.machine.referenced");
            referencedException.addParam(machineDetailProcessing.getId());
            throw referencedException;
        }
    }

    @EventListener(BeforeDeleteProductionOrderModels.class)
    public void on(final BeforeDeleteProductionOrderModels event) {
        final ReferencedException referencedException = new ReferencedException();
        final DetailProcessing productionOrderDetailProcessing = detailProcessingRepository.findFirstByProductionOrderProductionOrderId(event.getProductionOrderId());
        if (productionOrderDetailProcessing != null) {
            referencedException.setKey("productionOrderModels.detailProcessing.productionOrder.referenced");
            referencedException.addParam(productionOrderDetailProcessing.getId());
            throw referencedException;
        }
    }

}
