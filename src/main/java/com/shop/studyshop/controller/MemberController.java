package com.shop.studyshop.controller;

import com.shop.studyshop.dto.MemberFromDto;
import com.shop.studyshop.entity.Member;
import com.shop.studyshop.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new")
    public String memberFrom(Model model){
        model.addAttribute("memberFromDto",new MemberFromDto());
        return "member/memberFrom";
    }

    @PostMapping(value = "/new")
    public String newMember(@Valid MemberFromDto memberFromDto,
                            BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "member/memberFrom";
        }
        try {
            Member member = Member.createMember(memberFromDto,passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "member/memberFrom";
        }
        return "redirect:/";
    }
}
