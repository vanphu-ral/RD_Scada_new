package io.bootify.my_app.service;

import io.bootify.my_app.domain.LedwireProcessing;
import io.bootify.my_app.domain.MachinesModels;
import io.bootify.my_app.domain.ProductionOrderModels;
import io.bootify.my_app.events.BeforeDeleteMachinesModels;
import io.bootify.my_app.events.BeforeDeleteProductionOrderModels;
import io.bootify.my_app.model.LedwireProcessingDTO;
import io.bootify.my_app.repos.LedwireProcessingRepository;
import io.bootify.my_app.repos.MachinesModelsRepository;
import io.bootify.my_app.repos.ProductionOrderModelsRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.ReferencedException;
import java.util.List;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LedwireProcessingService {

    private final LedwireProcessingRepository ledwireProcessingRepository;
    private final MachinesModelsRepository machinesModelsRepository;
    private final ProductionOrderModelsRepository productionOrderModelsRepository;

    public LedwireProcessingService(final LedwireProcessingRepository ledwireProcessingRepository,
            final MachinesModelsRepository machinesModelsRepository,
            final ProductionOrderModelsRepository productionOrderModelsRepository) {
        this.ledwireProcessingRepository = ledwireProcessingRepository;
        this.machinesModelsRepository = machinesModelsRepository;
        this.productionOrderModelsRepository = productionOrderModelsRepository;
    }

    public List<LedwireProcessingDTO> findAll() {
        final List<LedwireProcessing> ledwireProcessings = ledwireProcessingRepository.findAll(Sort.by("id"));
        return ledwireProcessings.stream()
                .map(ledwireProcessing -> mapToDTO(ledwireProcessing, new LedwireProcessingDTO()))
                .toList();
    }

    public LedwireProcessingDTO get(final Integer id) {
        return ledwireProcessingRepository.findById(id)
                .map(ledwireProcessing -> mapToDTO(ledwireProcessing, new LedwireProcessingDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final LedwireProcessingDTO ledwireProcessingDTO) {
        final LedwireProcessing ledwireProcessing = new LedwireProcessing();
        mapToEntity(ledwireProcessingDTO, ledwireProcessing);
        return ledwireProcessingRepository.save(ledwireProcessing).getId();
    }

    public void update(final Integer id, final LedwireProcessingDTO ledwireProcessingDTO) {
        final LedwireProcessing ledwireProcessing = ledwireProcessingRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(ledwireProcessingDTO, ledwireProcessing);
        ledwireProcessingRepository.save(ledwireProcessing);
    }

    public void delete(final Integer id) {
        final LedwireProcessing ledwireProcessing = ledwireProcessingRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        ledwireProcessingRepository.delete(ledwireProcessing);
    }

    private LedwireProcessingDTO mapToDTO(final LedwireProcessing ledwireProcessing,
            final LedwireProcessingDTO ledwireProcessingDTO) {
        ledwireProcessingDTO.setId(ledwireProcessing.getId());
        ledwireProcessingDTO.setMachineStatus(ledwireProcessing.getMachineStatus());
        ledwireProcessingDTO.setMachineWorking(ledwireProcessing.getMachineWorking());
        ledwireProcessingDTO.setNumberOfInput(ledwireProcessing.getNumberOfInput());
        ledwireProcessingDTO.setNumberOfOutput(ledwireProcessing.getNumberOfOutput());
        ledwireProcessingDTO.setPlasticUsed(ledwireProcessing.getPlasticUsed());
        ledwireProcessingDTO.setRunningTime(ledwireProcessing.getRunningTime());
        ledwireProcessingDTO.setStoppingTime(ledwireProcessing.getStoppingTime());
        ledwireProcessingDTO.setCycleTime(ledwireProcessing.getCycleTime());
        ledwireProcessingDTO.setE1(ledwireProcessing.getE1());
        ledwireProcessingDTO.setE2(ledwireProcessing.getE2());
        ledwireProcessingDTO.setE3(ledwireProcessing.getE3());
        ledwireProcessingDTO.setE4(ledwireProcessing.getE4());
        ledwireProcessingDTO.setE5(ledwireProcessing.getE5());
        ledwireProcessingDTO.setE6(ledwireProcessing.getE6());
        ledwireProcessingDTO.setE7(ledwireProcessing.getE7());
        ledwireProcessingDTO.setE8(ledwireProcessing.getE8());
        ledwireProcessingDTO.setE9(ledwireProcessing.getE9());
        ledwireProcessingDTO.setE10(ledwireProcessing.getE10());
        ledwireProcessingDTO.setE11(ledwireProcessing.getE11());
        ledwireProcessingDTO.setE12(ledwireProcessing.getE12());
        ledwireProcessingDTO.setE13(ledwireProcessing.getE13());
        ledwireProcessingDTO.setE14(ledwireProcessing.getE14());
        ledwireProcessingDTO.setE15(ledwireProcessing.getE15());
        ledwireProcessingDTO.setE16(ledwireProcessing.getE16());
        ledwireProcessingDTO.setE17(ledwireProcessing.getE17());
        ledwireProcessingDTO.setE18(ledwireProcessing.getE18());
        ledwireProcessingDTO.setE19(ledwireProcessing.getE19());
        ledwireProcessingDTO.setE20(ledwireProcessing.getE20());
        ledwireProcessingDTO.setTemp1(ledwireProcessing.getTemp1());
        ledwireProcessingDTO.setTemp2(ledwireProcessing.getTemp2());
        ledwireProcessingDTO.setTemp3(ledwireProcessing.getTemp3());
        ledwireProcessingDTO.setTemp4(ledwireProcessing.getTemp4());
        ledwireProcessingDTO.setTemp5(ledwireProcessing.getTemp5());
        ledwireProcessingDTO.setTemp6(ledwireProcessing.getTemp6());
        ledwireProcessingDTO.setTemp7(ledwireProcessing.getTemp7());
        ledwireProcessingDTO.setTemp8(ledwireProcessing.getTemp8());
        ledwireProcessingDTO.setSpeed(ledwireProcessing.getSpeed());
        ledwireProcessingDTO.setMachine(ledwireProcessing.getMachine() == null ? null : ledwireProcessing.getMachine().getMachineId());
        ledwireProcessingDTO.setProductionOrder(ledwireProcessing.getProductionOrder() == null ? null : ledwireProcessing.getProductionOrder().getProductionOrderId());
        return ledwireProcessingDTO;
    }

    private LedwireProcessing mapToEntity(final LedwireProcessingDTO ledwireProcessingDTO,
            final LedwireProcessing ledwireProcessing) {
        ledwireProcessing.setMachineStatus(ledwireProcessingDTO.getMachineStatus());
        ledwireProcessing.setMachineWorking(ledwireProcessingDTO.getMachineWorking());
        ledwireProcessing.setNumberOfInput(ledwireProcessingDTO.getNumberOfInput());
        ledwireProcessing.setNumberOfOutput(ledwireProcessingDTO.getNumberOfOutput());
        ledwireProcessing.setPlasticUsed(ledwireProcessingDTO.getPlasticUsed());
        ledwireProcessing.setRunningTime(ledwireProcessingDTO.getRunningTime());
        ledwireProcessing.setStoppingTime(ledwireProcessingDTO.getStoppingTime());
        ledwireProcessing.setCycleTime(ledwireProcessingDTO.getCycleTime());
        ledwireProcessing.setE1(ledwireProcessingDTO.getE1());
        ledwireProcessing.setE2(ledwireProcessingDTO.getE2());
        ledwireProcessing.setE3(ledwireProcessingDTO.getE3());
        ledwireProcessing.setE4(ledwireProcessingDTO.getE4());
        ledwireProcessing.setE5(ledwireProcessingDTO.getE5());
        ledwireProcessing.setE6(ledwireProcessingDTO.getE6());
        ledwireProcessing.setE7(ledwireProcessingDTO.getE7());
        ledwireProcessing.setE8(ledwireProcessingDTO.getE8());
        ledwireProcessing.setE9(ledwireProcessingDTO.getE9());
        ledwireProcessing.setE10(ledwireProcessingDTO.getE10());
        ledwireProcessing.setE11(ledwireProcessingDTO.getE11());
        ledwireProcessing.setE12(ledwireProcessingDTO.getE12());
        ledwireProcessing.setE13(ledwireProcessingDTO.getE13());
        ledwireProcessing.setE14(ledwireProcessingDTO.getE14());
        ledwireProcessing.setE15(ledwireProcessingDTO.getE15());
        ledwireProcessing.setE16(ledwireProcessingDTO.getE16());
        ledwireProcessing.setE17(ledwireProcessingDTO.getE17());
        ledwireProcessing.setE18(ledwireProcessingDTO.getE18());
        ledwireProcessing.setE19(ledwireProcessingDTO.getE19());
        ledwireProcessing.setE20(ledwireProcessingDTO.getE20());
        ledwireProcessing.setTemp1(ledwireProcessingDTO.getTemp1());
        ledwireProcessing.setTemp2(ledwireProcessingDTO.getTemp2());
        ledwireProcessing.setTemp3(ledwireProcessingDTO.getTemp3());
        ledwireProcessing.setTemp4(ledwireProcessingDTO.getTemp4());
        ledwireProcessing.setTemp5(ledwireProcessingDTO.getTemp5());
        ledwireProcessing.setTemp6(ledwireProcessingDTO.getTemp6());
        ledwireProcessing.setTemp7(ledwireProcessingDTO.getTemp7());
        ledwireProcessing.setTemp8(ledwireProcessingDTO.getTemp8());
        ledwireProcessing.setSpeed(ledwireProcessingDTO.getSpeed());
        final MachinesModels machine = ledwireProcessingDTO.getMachine() == null ? null : machinesModelsRepository.findById(ledwireProcessingDTO.getMachine())
                .orElseThrow(() -> new NotFoundException("machine not found"));
        ledwireProcessing.setMachine(machine);
        final ProductionOrderModels productionOrder = ledwireProcessingDTO.getProductionOrder() == null ? null : productionOrderModelsRepository.findById(ledwireProcessingDTO.getProductionOrder())
                .orElseThrow(() -> new NotFoundException("productionOrder not found"));
        ledwireProcessing.setProductionOrder(productionOrder);
        return ledwireProcessing;
    }

    @EventListener(BeforeDeleteMachinesModels.class)
    public void on(final BeforeDeleteMachinesModels event) {
        final ReferencedException referencedException = new ReferencedException();
        final LedwireProcessing machineLedwireProcessing = ledwireProcessingRepository.findFirstByMachineMachineId(event.getMachineId());
        if (machineLedwireProcessing != null) {
            referencedException.setKey("machinesModels.ledwireProcessing.machine.referenced");
            referencedException.addParam(machineLedwireProcessing.getId());
            throw referencedException;
        }
    }

    @EventListener(BeforeDeleteProductionOrderModels.class)
    public void on(final BeforeDeleteProductionOrderModels event) {
        final ReferencedException referencedException = new ReferencedException();
        final LedwireProcessing productionOrderLedwireProcessing = ledwireProcessingRepository.findFirstByProductionOrderProductionOrderId(event.getProductionOrderId());
        if (productionOrderLedwireProcessing != null) {
            referencedException.setKey("productionOrderModels.ledwireProcessing.productionOrder.referenced");
            referencedException.addParam(productionOrderLedwireProcessing.getId());
            throw referencedException;
        }
    }

}
