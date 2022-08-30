package com.example.currencyalerts.Models;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Serializable {

    public enum Role {
        USER,
        ADMIN;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Enumerated
    @Column(name = "role")
    private Role role;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role=" + role +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}