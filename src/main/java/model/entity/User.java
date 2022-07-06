package model.entity;

import java.util.Objects;
import java.util.UUID;

public class User {
    private UUID id;
    private String login;
    private String password;
    private UserDetails userDetails;

    private User(Builder builder) {
        this.id = builder.id;
        this.login = builder.login;
        this.password = builder.password;
        this.userDetails = builder.userDetails;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !getClass().equals(o.getClass())) {
            return false;
        }
        User user = (User) o;

        return getId().equals(user.getId())
                && getLogin().equals(user.getLogin())
                && Objects.equals(getUserDetails(), user.getUserDetails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogin(), userDetails);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", userDetails=" + userDetails +
                '}';
    }

    public static class Builder {
        private UUID id;
        private String login;
        private String password;
        private UserDetails userDetails;

        public Builder setId(UUID id) {
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

        public Builder setUserDetails(UserDetails userDetails) {
            this.userDetails = userDetails;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }
}
