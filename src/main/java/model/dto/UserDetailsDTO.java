package model.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class UserDetailsDTO {
    private final UUID id;
    private final String firstName;
    private final String lastName;
    private final Integer passportNum;
    private final String email;
    private final Timestamp createdAt;
    private final Timestamp modifiedAt;

    private UserDetailsDTO(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.passportNum = builder.passportNum;
        this.createdAt = builder.createdAt;
        this.modifiedAt = builder.modifiedAt;
        this.email = builder.email;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getPassportNum() {
        return passportNum;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !getClass().equals(o.getClass())) {
            return false;
        }

        UserDetailsDTO userDetailsDTO = (UserDetailsDTO) o;

        return getPassportNum().equals(userDetailsDTO.getPassportNum());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPassportNum());
    }

    public static class Builder {
        private UUID id;
        private String firstName;
        private String lastName;
        private Integer passportNum;
        public String email;
        private Timestamp createdAt;
        private Timestamp modifiedAt;

        public Builder setId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setPassportNum(Integer passportNum) {
            this.passportNum = passportNum;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setCreatedAt(Timestamp createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setModifiedAt(Timestamp modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public UserDetailsDTO build() {
            return new UserDetailsDTO(this);
        }
    }
}
