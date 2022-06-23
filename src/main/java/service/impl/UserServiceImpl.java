package service.impl;

import mapper.CustomMapper;
import model.dto.UserDTO;
import model.entity.User;
import repo.UserRepo;
import service.UserDetailsService;
import service.UserService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final UserDetailsService userDetailsService;

    public UserServiceImpl(UserRepo userRepo, UserDetailsService userDetailsService) {
        this.userRepo = userRepo;
        this.userDetailsService = userDetailsService;
    }


    @Override
    public UserDTO create(User user) {
        userDetailsService.create(user.getUserDetails());
        return CustomMapper.toDTO(userRepo.create(user));
    }

    @Override
    public UserDTO read(UUID id) {
        return CustomMapper.toDTO(userRepo.read(id));
    }

    @Override
    public UserDTO update(User user) {
        userDetailsService.update(user.getUserDetails());
        return CustomMapper.toDTO(userRepo.update(user));
    }

    @Override
    public void delete(UUID id) {
        userRepo.delete(id);
    }

    @Override
    public List<UserDTO> readAll() {
        return userRepo.findAll().stream()
                .map(CustomMapper::toDTO)
                .collect(Collectors.toList());
    }
}
