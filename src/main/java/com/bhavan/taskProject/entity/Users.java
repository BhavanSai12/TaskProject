package com.bhavan.taskProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users",uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "Name",nullable = false)
    private String name;
    @Column(name = "Email",nullable = false)
    private String email;
    @Column(name = "Password",nullable = false)
    private String password;
}
