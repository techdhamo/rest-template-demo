package com.otomate.loginservice.model;

import javax.persistence.*;

import lombok.Data;
@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 
    @Column(unique = true)
    private String name; 
       
}