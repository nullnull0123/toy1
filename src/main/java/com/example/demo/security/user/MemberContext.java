package com.example.demo.security.user;

import com.example.demo.members.domain.entity.Members;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class MemberContext extends User {

    private final Members members;

    public MemberContext(Members members, Collection<? extends GrantedAuthority> authorities) {
        super(members.getUsername(), members.getPassword(), authorities);
        this.members = members;
    }

    public Members getMember(){
        return members;
    }
}
