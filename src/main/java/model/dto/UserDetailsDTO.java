package model.dto;

import java.util.UUID;

public record UserDetailsDTO(UUID id,
                             String firstName,
                             String lastName,
                             Integer passportNum,
                             String email) {
}
