package com.example.demo.members.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class Members {

    @GeneratedValue
    @Column(name = "user_sequence")
    @Id
    private Long sequence;

    @Column(unique = true)
    private String username;

    private String password;

    @Builder
    public Members(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
