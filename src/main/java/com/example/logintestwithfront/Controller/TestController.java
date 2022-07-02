package com.example.logintestwithfront.Controller;

import com.example.logintestwithfront.DTO.AuthMemberDTO;
import com.example.logintestwithfront.JWT.JwtProperties;
import com.example.logintestwithfront.Model.Oauth.KakaoProfile;
import com.example.logintestwithfront.Model.User;
import com.example.logintestwithfront.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api")
@RequiredArgsConstructor
@RestController
public class TestController {

    // 사용자 ID 받아오기
    @GetMapping("/header")
    public void requestSomething2(@RequestHeader(JwtProperties.USER_ID) String value) {
        System.out.println(value);
    }

}
