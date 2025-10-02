package io.bootify.my_app.service;

import io.bootify.my_app.domain.DevicesModels;
import io.bootify.my_app.model.DevicesModelsDTO;
import io.bootify.my_app.repos.DevicesModelsRepository;
import io.bootify.my_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DevicesModelsService {

    private final DevicesModelsRepository devicesModelsRepository;

    public DevicesModelsService(final DevicesModelsRepository devicesModelsRepository) {
        this.devicesModelsRepository = devicesModelsRepository;
    }

    public List<DevicesModelsDTO> findAll() {
        final List<DevicesModels> devicesModelses = devicesModelsRepository.findAll(Sort.by("devicesId"));
        return devicesModelses.stream()
                .map(devicesModels -> mapToDTO(devicesModels, new DevicesModelsDTO()))
                .toList();
    }

    public DevicesModelsDTO get(final Integer devicesId) {
        return devicesModelsRepository.findById(devicesId)
                .map(devicesModels -> mapToDTO(devicesModels, new DevicesModelsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final DevicesModelsDTO devicesModelsDTO) {
        final DevicesModels devicesModels = new DevicesModels();
        mapToEntity(devicesModelsDTO, devicesModels);
        return devicesModelsRepository.save(devicesModels).getDevicesId();
    }

    public void update(final Integer devicesId, final DevicesModelsDTO devicesModelsDTO) {
        final DevicesModels devicesModels = devicesModelsRepository.findById(devicesId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(devicesModelsDTO, devicesModels);
        devicesModelsRepository.save(devicesModels);
    }

    public void delete(final Integer devicesId) {
        final DevicesModels devicesModels = devicesModelsRepository.findById(devicesId)
                .orElseThrow(NotFoundException::new);
        devicesModelsRepository.delete(devicesModels);
    }

    private DevicesModelsDTO mapToDTO(final DevicesModels devicesModels,
            final DevicesModelsDTO devicesModelsDTO) {
        devicesModelsDTO.setDevicesId(devicesModels.getDevicesId());
        devicesModelsDTO.setDaqName(devicesModels.getDaqName());
        devicesModelsDTO.setHmiName(devicesModels.getHmiName());
        return devicesModelsDTO;
    }

    private DevicesModels mapToEntity(final DevicesModelsDTO devicesModelsDTO,
            final DevicesModels devicesModels) {
        devicesModels.setDaqName(devicesModelsDTO.getDaqName());
        devicesModels.setHmiName(devicesModelsDTO.getHmiName());
        return devicesModels;
    }

}
