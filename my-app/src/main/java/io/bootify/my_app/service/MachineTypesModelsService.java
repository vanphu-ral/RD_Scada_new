package io.bootify.my_app.service;

import io.bootify.my_app.domain.MachineTypesModels;
import io.bootify.my_app.events.BeforeDeleteMachineTypesModels;
import io.bootify.my_app.model.MachineTypesModelsDTO;
import io.bootify.my_app.repos.MachineTypesModelsRepository;
import io.bootify.my_app.util.NotFoundException;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MachineTypesModelsService {

    private final MachineTypesModelsRepository machineTypesModelsRepository;
    private final ApplicationEventPublisher publisher;

    public MachineTypesModelsService(
            final MachineTypesModelsRepository machineTypesModelsRepository,
            final ApplicationEventPublisher publisher) {
        this.machineTypesModelsRepository = machineTypesModelsRepository;
        this.publisher = publisher;
    }

    public List<MachineTypesModelsDTO> findAll() {
        final List<MachineTypesModels> machineTypesModelses = machineTypesModelsRepository.findAll(Sort.by("machineGroupId"));
        return machineTypesModelses.stream()
                .map(machineTypesModels -> mapToDTO(machineTypesModels, new MachineTypesModelsDTO()))
                .toList();
    }

    public MachineTypesModelsDTO get(final Integer machineGroupId) {
        return machineTypesModelsRepository.findById(machineGroupId)
                .map(machineTypesModels -> mapToDTO(machineTypesModels, new MachineTypesModelsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final MachineTypesModelsDTO machineTypesModelsDTO) {
        final MachineTypesModels machineTypesModels = new MachineTypesModels();
        mapToEntity(machineTypesModelsDTO, machineTypesModels);
        return machineTypesModelsRepository.save(machineTypesModels).getMachineGroupId();
    }

    public void update(final Integer machineGroupId,
            final MachineTypesModelsDTO machineTypesModelsDTO) {
        final MachineTypesModels machineTypesModels = machineTypesModelsRepository.findById(machineGroupId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(machineTypesModelsDTO, machineTypesModels);
        machineTypesModelsRepository.save(machineTypesModels);
    }

    public void delete(final Integer machineGroupId) {
        final MachineTypesModels machineTypesModels = machineTypesModelsRepository.findById(machineGroupId)
                .orElseThrow(NotFoundException::new);
        publisher.publishEvent(new BeforeDeleteMachineTypesModels(machineGroupId));
        machineTypesModelsRepository.delete(machineTypesModels);
    }

    public MachineTypesModelsDTO mapToDTO(final MachineTypesModels machineTypesModels,
            final MachineTypesModelsDTO machineTypesModelsDTO) {
        machineTypesModelsDTO.setMachineGroupId(machineTypesModels.getMachineGroupId());
        machineTypesModelsDTO.setMachineGroupName(machineTypesModels.getMachineGroupName());
        machineTypesModelsDTO.setDescriptions(machineTypesModels.getDescriptions());
        return machineTypesModelsDTO;
    }

    private MachineTypesModels mapToEntity(final MachineTypesModelsDTO machineTypesModelsDTO,
            final MachineTypesModels machineTypesModels) {
        machineTypesModels.setMachineGroupName(machineTypesModelsDTO.getMachineGroupName());
        machineTypesModels.setDescriptions(machineTypesModelsDTO.getDescriptions());
        return machineTypesModels;
    }

}
