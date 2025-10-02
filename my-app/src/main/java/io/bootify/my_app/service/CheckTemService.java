package io.bootify.my_app.service;

import io.bootify.my_app.domain.CheckTem;
import io.bootify.my_app.domain.ProfileTem;
import io.bootify.my_app.events.BeforeDeleteProfileTem;
import io.bootify.my_app.model.CheckTemDTO;
import io.bootify.my_app.repos.CheckTemRepository;
import io.bootify.my_app.repos.ProfileTemRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.ReferencedException;
import java.util.List;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CheckTemService {

    private final CheckTemRepository checkTemRepository;
    private final ProfileTemRepository profileTemRepository;

    public CheckTemService(final CheckTemRepository checkTemRepository,
            final ProfileTemRepository profileTemRepository) {
        this.checkTemRepository = checkTemRepository;
        this.profileTemRepository = profileTemRepository;
    }

    public List<CheckTemDTO> findAll() {
        final List<CheckTem> checkTems = checkTemRepository.findAll(Sort.by("checkId"));
        return checkTems.stream()
                .map(checkTem -> mapToDTO(checkTem, new CheckTemDTO()))
                .toList();
    }

    public CheckTemDTO get(final Long checkId) {
        return checkTemRepository.findById(checkId)
                .map(checkTem -> mapToDTO(checkTem, new CheckTemDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CheckTemDTO checkTemDTO) {
        final CheckTem checkTem = new CheckTem();
        mapToEntity(checkTemDTO, checkTem);
        return checkTemRepository.save(checkTem).getCheckId();
    }

    public void update(final Long checkId, final CheckTemDTO checkTemDTO) {
        final CheckTem checkTem = checkTemRepository.findById(checkId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(checkTemDTO, checkTem);
        checkTemRepository.save(checkTem);
    }

    public void delete(final Long checkId) {
        final CheckTem checkTem = checkTemRepository.findById(checkId)
                .orElseThrow(NotFoundException::new);
        checkTemRepository.delete(checkTem);
    }

    private CheckTemDTO mapToDTO(final CheckTem checkTem, final CheckTemDTO checkTemDTO) {
        checkTemDTO.setCheckId(checkTem.getCheckId());
        checkTemDTO.setPoName(checkTem.getPoName());
        checkTemDTO.setPassQlcl(checkTem.getPassQlcl());
        checkTemDTO.setNgQlcl(checkTem.getNgQlcl());
        checkTemDTO.setUserQlcl(checkTem.getUserQlcl());
        checkTemDTO.setPassBranch(checkTem.getPassBranch());
        checkTemDTO.setNgBranch(checkTem.getNgBranch());
        checkTemDTO.setUserBranch(checkTem.getUserBranch());
        checkTemDTO.setCheckedDate(checkTem.getCheckedDate());
        checkTemDTO.setStatusQlcl(checkTem.getStatusQlcl());
        checkTemDTO.setStatusBranch(checkTem.getStatusBranch());
        checkTemDTO.setTotals(checkTem.getTotals());
        checkTemDTO.setProfile(checkTem.getProfile() == null ? null : checkTem.getProfile().getProfileId());
        return checkTemDTO;
    }

    private CheckTem mapToEntity(final CheckTemDTO checkTemDTO, final CheckTem checkTem) {
        checkTem.setPoName(checkTemDTO.getPoName());
        checkTem.setPassQlcl(checkTemDTO.getPassQlcl());
        checkTem.setNgQlcl(checkTemDTO.getNgQlcl());
        checkTem.setUserQlcl(checkTemDTO.getUserQlcl());
        checkTem.setPassBranch(checkTemDTO.getPassBranch());
        checkTem.setNgBranch(checkTemDTO.getNgBranch());
        checkTem.setUserBranch(checkTemDTO.getUserBranch());
        checkTem.setCheckedDate(checkTemDTO.getCheckedDate());
        checkTem.setStatusQlcl(checkTemDTO.getStatusQlcl());
        checkTem.setStatusBranch(checkTemDTO.getStatusBranch());
        checkTem.setTotals(checkTemDTO.getTotals());
        final ProfileTem profile = checkTemDTO.getProfile() == null ? null : profileTemRepository.findById(checkTemDTO.getProfile())
                .orElseThrow(() -> new NotFoundException("profile not found"));
        checkTem.setProfile(profile);
        return checkTem;
    }

    @EventListener(BeforeDeleteProfileTem.class)
    public void on(final BeforeDeleteProfileTem event) {
        final ReferencedException referencedException = new ReferencedException();
        final CheckTem profileCheckTem = checkTemRepository.findFirstByProfileProfileId(event.getProfileId());
        if (profileCheckTem != null) {
            referencedException.setKey("profileTem.checkTem.profile.referenced");
            referencedException.addParam(profileCheckTem.getCheckId());
            throw referencedException;
        }
    }

}
