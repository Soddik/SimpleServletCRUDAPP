package mapper;

import model.dto.UserDTO;
import model.dto.UserDetailsDTO;
import model.entity.User;
import model.entity.UserDetails;

public class CustomMapper {

    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getLogin(), toDTO(user.getUserDetails()));
    }

    public static UserDetailsDTO toDTO(UserDetails userDetails) {
        return new UserDetailsDTO(
                userDetails.getId(),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                userDetails.getPassportNum(),
                userDetails.getEmail());
    }
}
