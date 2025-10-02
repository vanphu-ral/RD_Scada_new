package io.bootify.my_app.service;

import io.bootify.my_app.domain.DetailError;
import io.bootify.my_app.model.DetailErrorDTO;
import io.bootify.my_app.repos.DetailErrorRepository;
import io.bootify.my_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DetailErrorService {

    private final DetailErrorRepository detailErrorRepository;

    public DetailErrorService(final DetailErrorRepository detailErrorRepository) {
        this.detailErrorRepository = detailErrorRepository;
    }

    public List<DetailErrorDTO> findAll() {
        final List<DetailError> detailErrors = detailErrorRepository.findAll(Sort.by("detailEid"));
        return detailErrors.stream()
                .map(detailError -> mapToDTO(detailError, new DetailErrorDTO()))
                .toList();
    }

    public DetailErrorDTO get(final Long detailEid) {
        return detailErrorRepository.findById(detailEid)
                .map(detailError -> mapToDTO(detailError, new DetailErrorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DetailErrorDTO detailErrorDTO) {
        final DetailError detailError = new DetailError();
        mapToEntity(detailErrorDTO, detailError);
        return detailErrorRepository.save(detailError).getDetailEid();
    }

    public void update(final Long detailEid, final DetailErrorDTO detailErrorDTO) {
        final DetailError detailError = detailErrorRepository.findById(detailEid)
                .orElseThrow(NotFoundException::new);
        mapToEntity(detailErrorDTO, detailError);
        detailErrorRepository.save(detailError);
    }

    public void delete(final Long detailEid) {
        final DetailError detailError = detailErrorRepository.findById(detailEid)
                .orElseThrow(NotFoundException::new);
        detailErrorRepository.delete(detailError);
    }

    private DetailErrorDTO mapToDTO(final DetailError detailError,
            final DetailErrorDTO detailErrorDTO) {
        detailErrorDTO.setDetailEid(detailError.getDetailEid());
        detailErrorDTO.setWorkOrder(detailError.getWorkOrder());
        detailErrorDTO.setMachineName(detailError.getMachineName());
        detailErrorDTO.setErrorName(detailError.getErrorName());
        detailErrorDTO.setCreatedTime(detailError.getCreatedTime());
        return detailErrorDTO;
    }

    private DetailError mapToEntity(final DetailErrorDTO detailErrorDTO,
            final DetailError detailError) {
        detailError.setWorkOrder(detailErrorDTO.getWorkOrder());
        detailError.setMachineName(detailErrorDTO.getMachineName());
        detailError.setErrorName(detailErrorDTO.getErrorName());
        detailError.setCreatedTime(detailErrorDTO.getCreatedTime());
        return detailError;
    }

}
