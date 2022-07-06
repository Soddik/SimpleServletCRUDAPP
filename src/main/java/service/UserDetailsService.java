package service;

import mapper.UserDetailsMapper;
import model.dto.UserDetailsDTO;
import model.entity.UserDetails;
import repo.UserDetailsRepo;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserDetailsService {
    private final UserDetailsRepo userDetailsRepo;

    public UserDetailsService(UserDetailsRepo userDetailsRepo) {
        this.userDetailsRepo = userDetailsRepo;
    }


    public UserDetailsDTO create(UserDetails userDetails) {
        return UserDetailsMapper.toDTO(userDetailsRepo.create(userDetails));
    }


    public UserDetailsDTO read(UUID id) {
        return UserDetailsMapper.toDTO(userDetailsRepo.read(id));
    }


    public UserDetailsDTO update(UserDetails userDetails) {
        return UserDetailsMapper.toDTO(userDetailsRepo.update(userDetails));
    }


    public void delete(UUID id) {
        userDetailsRepo.delete(id);
    }


    public List<UserDetailsDTO> readAll() {
        return userDetailsRepo.findAll().stream()
                .map(UserDetailsMapper::toDTO)
                .collect(Collectors.toList());
    }
}
