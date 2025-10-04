package io.bootify.my_app.service;

import io.bootify.my_app.domain.LineProductionsModels;
import io.bootify.my_app.domain.MachineTypesModels;
import io.bootify.my_app.domain.MachinesModels;
import io.bootify.my_app.events.BeforeDeleteLineProductionsModels;
import io.bootify.my_app.events.BeforeDeleteMachineTypesModels;
import io.bootify.my_app.events.BeforeDeleteMachinesModels;
import io.bootify.my_app.model.MachinesModelsDTO;
import io.bootify.my_app.repos.LineProductionsModelsRepository;
import io.bootify.my_app.repos.MachineTypesModelsRepository;
import io.bootify.my_app.repos.MachinesModelsRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.ReferencedException;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MachinesModelsService {

    private final MachinesModelsRepository machinesModelsRepository;
    private final MachineTypesModelsRepository machineTypesModelsRepository;
    private final LineProductionsModelsRepository lineProductionsModelsRepository;
    private final ApplicationEventPublisher publisher;

    public MachinesModelsService(final MachinesModelsRepository machinesModelsRepository,
            final MachineTypesModelsRepository machineTypesModelsRepository,
            final LineProductionsModelsRepository lineProductionsModelsRepository,
            final ApplicationEventPublisher publisher) {
        this.machinesModelsRepository = machinesModelsRepository;
        this.machineTypesModelsRepository = machineTypesModelsRepository;
        this.lineProductionsModelsRepository = lineProductionsModelsRepository;
        this.publisher = publisher;
    }

    public List<MachinesModelsDTO> findAll() {
        final List<MachinesModels> machinesModelses = machinesModelsRepository.findAll(Sort.by("machineId"));
        return machinesModelses.stream()
                .map(machinesModels -> mapToDTO(machinesModels, new MachinesModelsDTO()))
                .toList();
    }

    public MachinesModelsDTO get(final Integer machineId) {
        return machinesModelsRepository.findById(machineId)
                .map(machinesModels -> mapToDTO(machinesModels, new MachinesModelsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final MachinesModelsDTO machinesModelsDTO) {
        final MachinesModels machinesModels = new MachinesModels();
        mapToEntity(machinesModelsDTO, machinesModels);
        return machinesModelsRepository.save(machinesModels).getMachineId();
    }

    public void update(final Integer machineId, final MachinesModelsDTO machinesModelsDTO) {
        final MachinesModels machinesModels = machinesModelsRepository.findById(machineId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(machinesModelsDTO, machinesModels);
        machinesModelsRepository.save(machinesModels);
    }

    public void delete(final Integer machineId) {
        final MachinesModels machinesModels = machinesModelsRepository.findById(machineId)
                .orElseThrow(NotFoundException::new);
        publisher.publishEvent(new BeforeDeleteMachinesModels(machineId));
        machinesModelsRepository.delete(machinesModels);
    }

    public MachinesModelsDTO mapToDTO(final MachinesModels machinesModels,
            final MachinesModelsDTO machinesModelsDTO) {
        machinesModelsDTO.setMachineId(machinesModels.getMachineId());
        machinesModelsDTO.setMachineName(machinesModels.getMachineName());
        machinesModelsDTO.setStageId(machinesModels.getStageId());
        machinesModelsDTO.setMachineGroup(machinesModels.getMachineGroup() == null ? null : machinesModels.getMachineGroup().getMachineGroupId());
        machinesModelsDTO.setLine(machinesModels.getLine() == null ? null : machinesModels.getLine().getLineId());
        return machinesModelsDTO;
    }

    private MachinesModels mapToEntity(final MachinesModelsDTO machinesModelsDTO,
            final MachinesModels machinesModels) {
        machinesModels.setMachineName(machinesModelsDTO.getMachineName());
        machinesModels.setStageId(machinesModelsDTO.getStageId());
        final MachineTypesModels machineGroup = machinesModelsDTO.getMachineGroup() == null ? null : machineTypesModelsRepository.findById(machinesModelsDTO.getMachineGroup())
                .orElseThrow(() -> new NotFoundException("machineGroup not found"));
        machinesModels.setMachineGroup(machineGroup);
        final LineProductionsModels line = machinesModelsDTO.getLine() == null ? null : lineProductionsModelsRepository.findById(machinesModelsDTO.getLine())
                .orElseThrow(() -> new NotFoundException("line not found"));
        machinesModels.setLine(line);
        return machinesModels;
    }

    @EventListener(BeforeDeleteMachineTypesModels.class)
    public void on(final BeforeDeleteMachineTypesModels event) {
        final ReferencedException referencedException = new ReferencedException();
        final MachinesModels machineGroupMachinesModels = machinesModelsRepository.findFirstByMachineGroupMachineGroupId(event.getMachineGroupId());
        if (machineGroupMachinesModels != null) {
            referencedException.setKey("machineTypesModels.machinesModels.machineGroup.referenced");
            referencedException.addParam(machineGroupMachinesModels.getMachineId());
            throw referencedException;
        }
    }

    @EventListener(BeforeDeleteLineProductionsModels.class)
    public void on(final BeforeDeleteLineProductionsModels event) {
        final ReferencedException referencedException = new ReferencedException();
        final MachinesModels lineMachinesModels = machinesModelsRepository.findFirstByLineLineId(event.getLineId());
        if (lineMachinesModels != null) {
            referencedException.setKey("lineProductionsModels.machinesModels.line.referenced");
            referencedException.addParam(lineMachinesModels.getMachineId());
            throw referencedException;
        }
    }

}
