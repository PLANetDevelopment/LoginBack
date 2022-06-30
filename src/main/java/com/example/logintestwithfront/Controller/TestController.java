package com.example.logintestwithfront.Controller;

import com.example.logintestwithfront.DTO.AuthMemberDTO;
import com.example.logintestwithfront.Model.Oauth.KakaoProfile;
import com.example.logintestwithfront.Model.User;
import com.example.logintestwithfront.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
//@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class TestController {

    private final UserRepository userRepository;

    @GetMapping("/api/test1")
    public String test1(@AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        System.out.println("test1 started...");
        System.out.println(authMemberDTO.getEmail());
        return authMemberDTO.getEmail();
    }

    @GetMapping("/test2")
    public String test2(@AuthenticationPrincipal User user) {
        System.out.println("test2 started...");
        return user.getKakaoEmail();
    }

    @GetMapping("/test3")
    public String test3(@AuthenticationPrincipal KakaoProfile kakaoProfile) {
        System.out.println("test3 started...");
        return kakaoProfile.toString();
    }

}
