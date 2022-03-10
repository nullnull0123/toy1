package com.example.demo.members.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.demo.security.user.MemberContext;
import com.example.demo.members.controller.dto.SignUpRequest;
import com.example.demo.members.domain.entity.Members;
import com.example.demo.members.domain.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    private final BCryptPasswordEncoder encoder;

    private final MemberRepository repository;

    public Members signup(SignUpRequest dto){
        String password = dto.getPassword();
        dto.setPassword(encoder.encode(password));
        return repository.save(dto.toEntity());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Members searchedMembers = repository.findByUsername(username).orElseThrow(() -> {
            log.info("회원 정보가 존재하지 않습니다.");
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        });

        // TODO : ROLE -> entity에 생성 및 DB에서 정보 가져올 것
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));

//        UserDetails memberInfo = new CustomUserDetails(searchedMember, roles);
        MemberContext memberInfo = new MemberContext(searchedMembers, roles);
        return memberInfo;
    }
}
