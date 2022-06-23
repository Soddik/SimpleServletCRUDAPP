package model.entity;

import java.util.Objects;
import java.util.UUID;

public class UserDetails {
    private UUID id;
    private String firstName;
    private String lastName;
    private Integer passportNum;
    private String email;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getPassportNum() {
        return passportNum;
    }

    public void setPassportNum(Integer passportNum) {
        this.passportNum = passportNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !getClass().equals(o.getClass())) {
            return false;
        }
        UserDetails userDetails = (UserDetails) o;

        return getId().equals(userDetails.getId())
                && getFirstName().equals(userDetails.getFirstName())
                && getLastName().equals(userDetails.getLastName())
                && getPassportNum().equals(userDetails.getPassportNum())
                && getEmail().equals(userDetails.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", passportNum=" + passportNum +
                ", email='" + email + '\'' +
                '}';
    }
}
