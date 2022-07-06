package service;

import mapper.UserMapper;
import model.dto.UserDTO;
import model.entity.User;
import repo.UserRepo;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserService {
    private final UserRepo userRepo;
    private final UserDetailsService userDetailsService;

    public UserService(UserRepo userRepo, UserDetailsService userDetailsService) {
        this.userRepo = userRepo;
        this.userDetailsService = userDetailsService;
    }

    public UserDTO create(User user) {
        userDetailsService.create(user.getUserDetails());
        return UserMapper.toDTO(userRepo.create(user));
    }

    public UserDTO read(UUID id) {
        return UserMapper.toDTO(userRepo.read(id));
    }

    public UserDTO update(User user) {
        userDetailsService.update(user.getUserDetails());
        return UserMapper.toDTO(userRepo.update(user));
    }

    public void delete(UUID id) {
        userRepo.delete(id);
    }

    public List<UserDTO> readAll() {
        return userRepo.findAll().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }
}
