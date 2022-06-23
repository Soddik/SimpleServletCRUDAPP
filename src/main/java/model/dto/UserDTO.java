package model.dto;

import java.util.UUID;

public record UserDTO(UUID uuid, String login, UserDetailsDTO userDetails) {
}
