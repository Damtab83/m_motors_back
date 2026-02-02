package com.dam.mmotors.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private Role role;

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    public boolean isCustomer() {
        return role == Role.CUSTOMER;
    }

}
