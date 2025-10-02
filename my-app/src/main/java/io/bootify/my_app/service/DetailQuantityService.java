package io.bootify.my_app.service;

import io.bootify.my_app.domain.DetailQuantity;
import io.bootify.my_app.model.DetailQuantityDTO;
import io.bootify.my_app.repos.DetailQuantityRepository;
import io.bootify.my_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DetailQuantityService {

    private final DetailQuantityRepository detailQuantityRepository;

    public DetailQuantityService(final DetailQuantityRepository detailQuantityRepository) {
        this.detailQuantityRepository = detailQuantityRepository;
    }

    public List<DetailQuantityDTO> findAll() {
        final List<DetailQuantity> detailQuantities = detailQuantityRepository.findAll(Sort.by("detailQid"));
        return detailQuantities.stream()
                .map(detailQuantity -> mapToDTO(detailQuantity, new DetailQuantityDTO()))
                .toList();
    }

    public DetailQuantityDTO get(final Long detailQid) {
        return detailQuantityRepository.findById(detailQid)
                .map(detailQuantity -> mapToDTO(detailQuantity, new DetailQuantityDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DetailQuantityDTO detailQuantityDTO) {
        final DetailQuantity detailQuantity = new DetailQuantity();
        mapToEntity(detailQuantityDTO, detailQuantity);
        return detailQuantityRepository.save(detailQuantity).getDetailQid();
    }

    public void update(final Long detailQid, final DetailQuantityDTO detailQuantityDTO) {
        final DetailQuantity detailQuantity = detailQuantityRepository.findById(detailQid)
                .orElseThrow(NotFoundException::new);
        mapToEntity(detailQuantityDTO, detailQuantity);
        detailQuantityRepository.save(detailQuantity);
    }

    public void delete(final Long detailQid) {
        final DetailQuantity detailQuantity = detailQuantityRepository.findById(detailQid)
                .orElseThrow(NotFoundException::new);
        detailQuantityRepository.delete(detailQuantity);
    }

    private DetailQuantityDTO mapToDTO(final DetailQuantity detailQuantity,
            final DetailQuantityDTO detailQuantityDTO) {
        detailQuantityDTO.setDetailQid(detailQuantity.getDetailQid());
        detailQuantityDTO.setWorkOrder(detailQuantity.getWorkOrder());
        detailQuantityDTO.setMachineName(detailQuantity.getMachineName());
        detailQuantityDTO.setNumberInput(detailQuantity.getNumberInput());
        detailQuantityDTO.setNumberOutput(detailQuantity.getNumberOutput());
        detailQuantityDTO.setRunTime(detailQuantity.getRunTime());
        detailQuantityDTO.setStoptime(detailQuantity.getStoptime());
        detailQuantityDTO.setInfoDay(detailQuantity.getInfoDay());
        return detailQuantityDTO;
    }

    private DetailQuantity mapToEntity(final DetailQuantityDTO detailQuantityDTO,
            final DetailQuantity detailQuantity) {
        detailQuantity.setWorkOrder(detailQuantityDTO.getWorkOrder());
        detailQuantity.setMachineName(detailQuantityDTO.getMachineName());
        detailQuantity.setNumberInput(detailQuantityDTO.getNumberInput());
        detailQuantity.setNumberOutput(detailQuantityDTO.getNumberOutput());
        detailQuantity.setRunTime(detailQuantityDTO.getRunTime());
        detailQuantity.setStoptime(detailQuantityDTO.getStoptime());
        detailQuantity.setInfoDay(detailQuantityDTO.getInfoDay());
        return detailQuantity;
    }

}
