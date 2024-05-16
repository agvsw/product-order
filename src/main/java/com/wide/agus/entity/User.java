package com.wide.agus.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", schema = "wide_test")
public class User extends Persistent {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
}
