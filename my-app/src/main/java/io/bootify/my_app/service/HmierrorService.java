package io.bootify.my_app.service;

import io.bootify.my_app.domain.Hmierror;
import io.bootify.my_app.events.BeforeDeleteHmierror;
import io.bootify.my_app.model.HmierrorDTO;
import io.bootify.my_app.repos.HmierrorRepository;
import io.bootify.my_app.util.NotFoundException;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class HmierrorService {

    private final HmierrorRepository hmierrorRepository;
    private final ApplicationEventPublisher publisher;

    public HmierrorService(final HmierrorRepository hmierrorRepository,
            final ApplicationEventPublisher publisher) {
        this.hmierrorRepository = hmierrorRepository;
        this.publisher = publisher;
    }

    public List<HmierrorDTO> findAll() {
        final List<Hmierror> hmierrors = hmierrorRepository.findAll(Sort.by("hmierrId"));
        return hmierrors.stream()
                .map(hmierror -> mapToDTO(hmierror, new HmierrorDTO()))
                .toList();
    }

    public HmierrorDTO get(final Integer hmierrId) {
        return hmierrorRepository.findById(hmierrId)
                .map(hmierror -> mapToDTO(hmierror, new HmierrorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final HmierrorDTO hmierrorDTO) {
        final Hmierror hmierror = new Hmierror();
        mapToEntity(hmierrorDTO, hmierror);
        return hmierrorRepository.save(hmierror).getHmierrId();
    }

    public void update(final Integer hmierrId, final HmierrorDTO hmierrorDTO) {
        final Hmierror hmierror = hmierrorRepository.findById(hmierrId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(hmierrorDTO, hmierror);
        hmierrorRepository.save(hmierror);
    }

    public void delete(final Integer hmierrId) {
        final Hmierror hmierror = hmierrorRepository.findById(hmierrId)
                .orElseThrow(NotFoundException::new);
        publisher.publishEvent(new BeforeDeleteHmierror(hmierrId));
        hmierrorRepository.delete(hmierror);
    }

    private HmierrorDTO mapToDTO(final Hmierror hmierror, final HmierrorDTO hmierrorDTO) {
        hmierrorDTO.setHmierrId(hmierror.getHmierrId());
        hmierrorDTO.setHmierrCode(hmierror.getHmierrCode());
        hmierrorDTO.setHmierrName(hmierror.getHmierrName());
        return hmierrorDTO;
    }

    private Hmierror mapToEntity(final HmierrorDTO hmierrorDTO, final Hmierror hmierror) {
        hmierror.setHmierrCode(hmierrorDTO.getHmierrCode());
        hmierror.setHmierrName(hmierrorDTO.getHmierrName());
        return hmierror;
    }

}
