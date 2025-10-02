package io.bootify.my_app.service;

import io.bootify.my_app.domain.UsersModels;
import io.bootify.my_app.model.UsersModelsDTO;
import io.bootify.my_app.repos.UsersModelsRepository;
import io.bootify.my_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UsersModelsService {

    private final UsersModelsRepository usersModelsRepository;

    public UsersModelsService(final UsersModelsRepository usersModelsRepository) {
        this.usersModelsRepository = usersModelsRepository;
    }

    public List<UsersModelsDTO> findAll() {
        final List<UsersModels> usersModelses = usersModelsRepository.findAll(Sort.by("userId"));
        return usersModelses.stream()
                .map(usersModels -> mapToDTO(usersModels, new UsersModelsDTO()))
                .toList();
    }

    public UsersModelsDTO get(final Integer userId) {
        return usersModelsRepository.findById(userId)
                .map(usersModels -> mapToDTO(usersModels, new UsersModelsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final UsersModelsDTO usersModelsDTO) {
        final UsersModels usersModels = new UsersModels();
        mapToEntity(usersModelsDTO, usersModels);
        return usersModelsRepository.save(usersModels).getUserId();
    }

    public void update(final Integer userId, final UsersModelsDTO usersModelsDTO) {
        final UsersModels usersModels = usersModelsRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(usersModelsDTO, usersModels);
        usersModelsRepository.save(usersModels);
    }

    public void delete(final Integer userId) {
        final UsersModels usersModels = usersModelsRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        usersModelsRepository.delete(usersModels);
    }

    private UsersModelsDTO mapToDTO(final UsersModels usersModels,
            final UsersModelsDTO usersModelsDTO) {
        usersModelsDTO.setUserId(usersModels.getUserId());
        usersModelsDTO.setUserName(usersModels.getUserName());
        usersModelsDTO.setPasswords(usersModels.getPasswords());
        usersModelsDTO.setPhoneNumber(usersModels.getPhoneNumber());
        usersModelsDTO.setDescriptions(usersModels.getDescriptions());
        return usersModelsDTO;
    }

    private UsersModels mapToEntity(final UsersModelsDTO usersModelsDTO,
            final UsersModels usersModels) {
        usersModels.setUserName(usersModelsDTO.getUserName());
        usersModels.setPasswords(usersModelsDTO.getPasswords());
        usersModels.setPhoneNumber(usersModelsDTO.getPhoneNumber());
        usersModels.setDescriptions(usersModelsDTO.getDescriptions());
        return usersModels;
    }

}
