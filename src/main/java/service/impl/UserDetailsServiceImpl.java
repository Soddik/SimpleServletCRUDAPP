package service.impl;

import mapper.CustomMapper;
import model.dto.UserDetailsDTO;
import model.entity.UserDetails;
import repo.UserDetailsRepo;
import service.UserDetailsService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDetailsRepo userDetailsRepo;

    public UserDetailsServiceImpl(UserDetailsRepo userDetailsRepo) {
        this.userDetailsRepo = userDetailsRepo;
    }

    @Override
    public UserDetailsDTO create(UserDetails userDetails) {
        return CustomMapper.toDTO(userDetailsRepo.create(userDetails));
    }

    @Override
    public UserDetailsDTO read(UUID id) {
        return CustomMapper.toDTO(userDetailsRepo.read(id));
    }

    @Override
    public UserDetailsDTO update(UserDetails userDetails) {
        return CustomMapper.toDTO(userDetailsRepo.update(userDetails));
    }

    @Override
    public void delete(UUID id) {
        userDetailsRepo.delete(id);
    }

    @Override
    public List<UserDetailsDTO> readAll() {
        return userDetailsRepo.findAll().stream()
                .map(CustomMapper::toDTO)
                .collect(Collectors.toList());
    }
}
