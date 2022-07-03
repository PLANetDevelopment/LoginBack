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

    @GetMapping("/header")
    public Map<String, Object> requestSomething(@RequestHeader Map<String, Object> requestHeader) {
        System.out.println("Header is ======================================================");
        System.out.println(requestHeader);
        return requestHeader;
    }

}
