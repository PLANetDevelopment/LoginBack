package com.example.logintestwithfront.Controller;

import com.example.logintestwithfront.Model.OauthToken;
import com.example.logintestwithfront.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {

    private UserService userService;

    // 프론트에서 인가코드 받아오는 url
    @GetMapping("/oauth/token")
    public OauthToken getLogin(@RequestParam("code") String code) {

        // 넘어온 인가 코드를 통해 access_token 발급
        OauthToken oauthToken = userService.getAccessToken(code);

        return oauthToken;
    }

}