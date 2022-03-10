package com.example.demo.members.controller.dto;

import lombok.*;
import com.example.demo.members.domain.entity.Members;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private String username;

    private String password;

    public Members toEntity(){
        return Members.builder().username(username).password(password).build();
    }
}
