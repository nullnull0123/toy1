package com.example.demo.members.controller;

import lombok.RequiredArgsConstructor;
import com.example.demo.members.controller.dto.SignUpRequest;
import com.example.demo.members.domain.entity.Members;
import com.example.demo.members.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("signup")
    public String signupForm(Model model) {
        return "members/signup";
    }

    @PostMapping("signup")
    public String doSignup(Model model, SignUpRequest dto) {
        Members result = memberService.signup(dto);
        return "redirect:/article";
    }
}
