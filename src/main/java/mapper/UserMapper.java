package mapper;

import model.dto.UserDTO;
import model.entity.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return new UserDTO.Builder()
                .setId(user.getId())
                .setLogin(user.getLogin())
                .setPassword(user.getPassword())
                .setUserDetails(UserDetailsMapper.toDTO(user.getUserDetails()))
                .build();
    }

    public static User toEntity(UserDTO userDTO){
        return new User.Builder()
                .setId(userDTO.getId())
                .setLogin(userDTO.getLogin())
                .setPassword(userDTO.getPassword())
                .setUserDetails(UserDetailsMapper.toEntity(userDTO.getUserDetailsDTO()))
                .build();
    }
}
