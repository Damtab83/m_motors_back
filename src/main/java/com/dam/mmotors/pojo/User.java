package com.dam.mmotors.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String email;
    private String password;

    public void setRole(Role role) {
        this.role = Role.CUSTOMER;
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "car_id")
    @JsonBackReference
    private List<Car> myCars;

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    public boolean isCustomer() {
        return role == Role.CUSTOMER;
    }



}
