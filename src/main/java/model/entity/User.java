package model.entity;

import java.util.Objects;
import java.util.UUID;

public class User {
    private UUID id;
    private String login;
    private String password;
    private UserDetails userDetails;

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
}
