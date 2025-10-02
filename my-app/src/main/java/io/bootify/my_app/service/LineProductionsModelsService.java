package io.bootify.my_app.service;

import io.bootify.my_app.domain.LineProductionsModels;
import io.bootify.my_app.events.BeforeDeleteLineProductionsModels;
import io.bootify.my_app.model.LineProductionsModelsDTO;
import io.bootify.my_app.repos.LineProductionsModelsRepository;
import io.bootify.my_app.util.NotFoundException;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LineProductionsModelsService {

    private final LineProductionsModelsRepository lineProductionsModelsRepository;
    private final ApplicationEventPublisher publisher;

    public LineProductionsModelsService(
            final LineProductionsModelsRepository lineProductionsModelsRepository,
            final ApplicationEventPublisher publisher) {
        this.lineProductionsModelsRepository = lineProductionsModelsRepository;
        this.publisher = publisher;
    }

    public List<LineProductionsModelsDTO> findAll() {
        final List<LineProductionsModels> lineProductionsModelses = lineProductionsModelsRepository.findAll(Sort.by("lineId"));
        return lineProductionsModelses.stream()
                .map(lineProductionsModels -> mapToDTO(lineProductionsModels, new LineProductionsModelsDTO()))
                .toList();
    }

    public LineProductionsModelsDTO get(final Integer lineId) {
        return lineProductionsModelsRepository.findById(lineId)
                .map(lineProductionsModels -> mapToDTO(lineProductionsModels, new LineProductionsModelsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final LineProductionsModelsDTO lineProductionsModelsDTO) {
        final LineProductionsModels lineProductionsModels = new LineProductionsModels();
        mapToEntity(lineProductionsModelsDTO, lineProductionsModels);
        return lineProductionsModelsRepository.save(lineProductionsModels).getLineId();
    }

    public void update(final Integer lineId,
            final LineProductionsModelsDTO lineProductionsModelsDTO) {
        final LineProductionsModels lineProductionsModels = lineProductionsModelsRepository.findById(lineId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(lineProductionsModelsDTO, lineProductionsModels);
        lineProductionsModelsRepository.save(lineProductionsModels);
    }

    public void delete(final Integer lineId) {
        final LineProductionsModels lineProductionsModels = lineProductionsModelsRepository.findById(lineId)
                .orElseThrow(NotFoundException::new);
        publisher.publishEvent(new BeforeDeleteLineProductionsModels(lineId));
        lineProductionsModelsRepository.delete(lineProductionsModels);
    }

    private LineProductionsModelsDTO mapToDTO(final LineProductionsModels lineProductionsModels,
            final LineProductionsModelsDTO lineProductionsModelsDTO) {
        lineProductionsModelsDTO.setLineId(lineProductionsModels.getLineId());
        lineProductionsModelsDTO.setLineName(lineProductionsModels.getLineName());
        lineProductionsModelsDTO.setDescriptions(lineProductionsModels.getDescriptions());
        return lineProductionsModelsDTO;
    }

    private LineProductionsModels mapToEntity(
            final LineProductionsModelsDTO lineProductionsModelsDTO,
            final LineProductionsModels lineProductionsModels) {
        lineProductionsModels.setLineName(lineProductionsModelsDTO.getLineName());
        lineProductionsModels.setDescriptions(lineProductionsModelsDTO.getDescriptions());
        return lineProductionsModels;
    }

}
