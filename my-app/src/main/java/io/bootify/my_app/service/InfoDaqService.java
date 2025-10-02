package io.bootify.my_app.service;

import io.bootify.my_app.domain.InfoDaq;
import io.bootify.my_app.model.InfoDaqDTO;
import io.bootify.my_app.repos.InfoDaqRepository;
import io.bootify.my_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class InfoDaqService {

    private final InfoDaqRepository infoDaqRepository;

    public InfoDaqService(final InfoDaqRepository infoDaqRepository) {
        this.infoDaqRepository = infoDaqRepository;
    }

    public List<InfoDaqDTO> findAll() {
        final List<InfoDaq> infoDaqs = infoDaqRepository.findAll(Sort.by("recordId"));
        return infoDaqs.stream()
                .map(infoDaq -> mapToDTO(infoDaq, new InfoDaqDTO()))
                .toList();
    }

    public InfoDaqDTO get(final Long recordId) {
        return infoDaqRepository.findById(recordId)
                .map(infoDaq -> mapToDTO(infoDaq, new InfoDaqDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final InfoDaqDTO infoDaqDTO) {
        final InfoDaq infoDaq = new InfoDaq();
        mapToEntity(infoDaqDTO, infoDaq);
        return infoDaqRepository.save(infoDaq).getRecordId();
    }

    public void update(final Long recordId, final InfoDaqDTO infoDaqDTO) {
        final InfoDaq infoDaq = infoDaqRepository.findById(recordId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(infoDaqDTO, infoDaq);
        infoDaqRepository.save(infoDaq);
    }

    public void delete(final Long recordId) {
        final InfoDaq infoDaq = infoDaqRepository.findById(recordId)
                .orElseThrow(NotFoundException::new);
        infoDaqRepository.delete(infoDaq);
    }

    private InfoDaqDTO mapToDTO(final InfoDaq infoDaq, final InfoDaqDTO infoDaqDTO) {
        infoDaqDTO.setRecordId(infoDaq.getRecordId());
        infoDaqDTO.setDeviceName(infoDaq.getDeviceName());
        infoDaqDTO.setProfileName(infoDaq.getProfileName());
        infoDaqDTO.setLeftRight(infoDaq.getLeftRight());
        infoDaqDTO.setFixtureNo(infoDaq.getFixtureNo());
        infoDaqDTO.setStartTime(infoDaq.getStartTime());
        infoDaqDTO.setEndTime(infoDaq.getEndTime());
        infoDaqDTO.setElapsedTime(infoDaq.getElapsedTime());
        infoDaqDTO.setResultTest(infoDaq.getResultTest());
        infoDaqDTO.setInputVoltage1(infoDaq.getInputVoltage1());
        infoDaqDTO.setInputFrequency1(infoDaq.getInputFrequency1());
        infoDaqDTO.setPInAMin1(infoDaq.getPInAMin1());
        infoDaqDTO.setPInAMax1(infoDaq.getPInAMax1());
        infoDaqDTO.setVInMin1(infoDaq.getVInMin1());
        infoDaqDTO.setVInMax1(infoDaq.getVInMax1());
        infoDaqDTO.setIInMin1(infoDaq.getIInMin1());
        infoDaqDTO.setIInMax1(infoDaq.getIInMax1());
        infoDaqDTO.setPInWMin1(infoDaq.getPInWMin1());
        infoDaqDTO.setPInWMax1(infoDaq.getPInWMax1());
        infoDaqDTO.setPFMin1(infoDaq.getPFMin1());
        infoDaqDTO.setPFMax1(infoDaq.getPFMax1());
        infoDaqDTO.setPPrev1(infoDaq.getPPrev1());
        infoDaqDTO.setVInPrev1(infoDaq.getVInPrev1());
        infoDaqDTO.setIInPrev1(infoDaq.getIInPrev1());
        infoDaqDTO.setPInPrev1(infoDaq.getPInPrev1());
        infoDaqDTO.setPFPrev1(infoDaq.getPFPrev1());
        infoDaqDTO.setInputVoltage2(infoDaq.getInputVoltage2());
        infoDaqDTO.setInputFrequency2(infoDaq.getInputFrequency2());
        infoDaqDTO.setPInAMin2(infoDaq.getPInAMin2());
        infoDaqDTO.setPInAMax2(infoDaq.getPInAMax2());
        infoDaqDTO.setVInMin2(infoDaq.getVInMin2());
        infoDaqDTO.setVInMax2(infoDaq.getVInMax2());
        infoDaqDTO.setIInMin2(infoDaq.getIInMin2());
        infoDaqDTO.setIInMax2(infoDaq.getIInMax2());
        infoDaqDTO.setPInWMin2(infoDaq.getPInWMin2());
        infoDaqDTO.setPInWMax2(infoDaq.getPInWMax2());
        infoDaqDTO.setPFMin2(infoDaq.getPFMin2());
        infoDaqDTO.setPFMax2(infoDaq.getPFMax2());
        infoDaqDTO.setPPrev2(infoDaq.getPPrev2());
        infoDaqDTO.setVInPrev2(infoDaq.getVInPrev2());
        infoDaqDTO.setIInPrev2(infoDaq.getIInPrev2());
        infoDaqDTO.setPInPrev2(infoDaq.getPInPrev2());
        infoDaqDTO.setPFPrev2(infoDaq.getPFPrev2());
        return infoDaqDTO;
    }

    private InfoDaq mapToEntity(final InfoDaqDTO infoDaqDTO, final InfoDaq infoDaq) {
        infoDaq.setDeviceName(infoDaqDTO.getDeviceName());
        infoDaq.setProfileName(infoDaqDTO.getProfileName());
        infoDaq.setLeftRight(infoDaqDTO.getLeftRight());
        infoDaq.setFixtureNo(infoDaqDTO.getFixtureNo());
        infoDaq.setStartTime(infoDaqDTO.getStartTime());
        infoDaq.setEndTime(infoDaqDTO.getEndTime());
        infoDaq.setElapsedTime(infoDaqDTO.getElapsedTime());
        infoDaq.setResultTest(infoDaqDTO.getResultTest());
        infoDaq.setInputVoltage1(infoDaqDTO.getInputVoltage1());
        infoDaq.setInputFrequency1(infoDaqDTO.getInputFrequency1());
        infoDaq.setPInAMin1(infoDaqDTO.getPInAMin1());
        infoDaq.setPInAMax1(infoDaqDTO.getPInAMax1());
        infoDaq.setVInMin1(infoDaqDTO.getVInMin1());
        infoDaq.setVInMax1(infoDaqDTO.getVInMax1());
        infoDaq.setIInMin1(infoDaqDTO.getIInMin1());
        infoDaq.setIInMax1(infoDaqDTO.getIInMax1());
        infoDaq.setPInWMin1(infoDaqDTO.getPInWMin1());
        infoDaq.setPInWMax1(infoDaqDTO.getPInWMax1());
        infoDaq.setPFMin1(infoDaqDTO.getPFMin1());
        infoDaq.setPFMax1(infoDaqDTO.getPFMax1());
        infoDaq.setPPrev1(infoDaqDTO.getPPrev1());
        infoDaq.setVInPrev1(infoDaqDTO.getVInPrev1());
        infoDaq.setIInPrev1(infoDaqDTO.getIInPrev1());
        infoDaq.setPInPrev1(infoDaqDTO.getPInPrev1());
        infoDaq.setPFPrev1(infoDaqDTO.getPFPrev1());
        infoDaq.setInputVoltage2(infoDaqDTO.getInputVoltage2());
        infoDaq.setInputFrequency2(infoDaqDTO.getInputFrequency2());
        infoDaq.setPInAMin2(infoDaqDTO.getPInAMin2());
        infoDaq.setPInAMax2(infoDaqDTO.getPInAMax2());
        infoDaq.setVInMin2(infoDaqDTO.getVInMin2());
        infoDaq.setVInMax2(infoDaqDTO.getVInMax2());
        infoDaq.setIInMin2(infoDaqDTO.getIInMin2());
        infoDaq.setIInMax2(infoDaqDTO.getIInMax2());
        infoDaq.setPInWMin2(infoDaqDTO.getPInWMin2());
        infoDaq.setPInWMax2(infoDaqDTO.getPInWMax2());
        infoDaq.setPFMin2(infoDaqDTO.getPFMin2());
        infoDaq.setPFMax2(infoDaqDTO.getPFMax2());
        infoDaq.setPPrev2(infoDaqDTO.getPPrev2());
        infoDaq.setVInPrev2(infoDaqDTO.getVInPrev2());
        infoDaq.setIInPrev2(infoDaqDTO.getIInPrev2());
        infoDaq.setPInPrev2(infoDaqDTO.getPInPrev2());
        infoDaq.setPFPrev2(infoDaqDTO.getPFPrev2());
        return infoDaq;
    }

}
