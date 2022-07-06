package mapper;

import model.dto.UserDetailsDTO;
import model.entity.UserDetails;

public class UserDetailsMapper {


    public static UserDetailsDTO toDTO(UserDetails userDetails) {
        return new UserDetailsDTO.Builder()
                .setId(userDetails.getId())
                .setFirstName(userDetails.getFirstName())
                .setLastName(userDetails.getLastName())
                .setPassportNum(userDetails.getPassportNum())
                .setEmail(userDetails.getEmail())
                .setCreatedAt(userDetails.getCreatedAt())
                .setModifiedAt(userDetails.getModifiedAt())
                .build();
    }

    public static UserDetails toEntity(UserDetailsDTO userDetailsDTO) {
        return new UserDetails.Builder()
                .setId(userDetailsDTO.getId())
                .setFirstName(userDetailsDTO.getFirstName())
                .setLastName(userDetailsDTO.getLastName())
                .setPassportNum(userDetailsDTO.getPassportNum())
                .setEmail(userDetailsDTO.getEmail())
                .setCreatedAt(userDetailsDTO.getCreatedAt())
                .setModifiedAt(userDetailsDTO.getModifiedAt())
                .build();
    }
}
