package model.dto;

import model.entity.User;
import model.entity.UserDetails;

import java.util.Objects;
import java.util.UUID;

public class UserDTO {
    private final UUID id;
    private final String login;
    private final String password;
    private final UserDetailsDTO userDetailsDTO;

    private UserDTO(Builder builder){
        this.id = builder.id;
        this.login = builder.login;
        this.password = builder.password;
        this.userDetailsDTO = builder.userDetails;
    }

    public UUID getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UserDetailsDTO getUserDetailsDTO() {
        return userDetailsDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !getClass().equals(o.getClass())) {
            return false;
        }

        UserDTO userDTO = (UserDTO) o;

        return getLogin().equals(userDTO.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin());
    }

    public static class Builder {
        private UUID id;
        private String login;
        private String password;
        private UserDetailsDTO userDetails;

        public  Builder setId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setUserDetails(UserDetailsDTO userDetails) {
            this.userDetails = userDetails;
            return this;
        }

        public UserDTO build(){
            return new UserDTO(this);
        }
    }
}
