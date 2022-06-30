package com.example.logintestwithfront.Controller;

import com.example.logintestwithfront.DTO.AuthMemberDTO;
import com.example.logintestwithfront.Model.Oauth.KakaoProfile;
import com.example.logintestwithfront.Model.User;
import com.example.logintestwithfront.Repository.UserRepository;
import io.jsonwebtoken.io.IOException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
public class TestController {

    private final UserRepository userRepository;

    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/test1")
    public String test1(@AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        return authMemberDTO.getEmail();
    }

    @GetMapping("/test2")
    public String test2(@AuthenticationPrincipal User user) {
        return user.getKakaoEmail();
    }

    @GetMapping("/test3")
    public String test3(@AuthenticationPrincipal KakaoProfile kakaoProfile) {
        return kakaoProfile.toString();
    }

}
