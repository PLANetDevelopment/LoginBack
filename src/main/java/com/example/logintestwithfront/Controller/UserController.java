package com.example.logintestwithfront.Controller;

import com.example.logintestwithfront.JWT.JwtProperties;
import com.example.logintestwithfront.Model.OauthToken;
import com.example.logintestwithfront.Service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/oauth/token") // 프론트에서 인가코드 받아오는 url
    public String getLogin(@RequestParam("code") String code) {
        // 넘어온 인가 코드를 통해 access_token 발급
        OauthToken oauthToken = userService.getAccessToken(code);

        // 발급 받은 accessToken 으로 카카오 회원 정보 DB 저장 후 JWT 를 생성
        String jwtToken = userService.saveUserAndGetToken(oauthToken.getAccess_token());

        // 응답 헤더의 Authorization 항목에 JWT를 넣음
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);

        String userId = userService.getUserId(oauthToken.getAccess_token());

        // 사용자 아이디를 헤더의 userId 항목에 담아 넘긴다.
//        headers.add(JwtProperties.USER_ID, userId);

        // JWT가 담긴 헤더와 200 ok, "success"라는 body를 ResponseEntity에 담아 프론트에 전달
//        return ResponseEntity.ok().headers(headers).body("success");

        return userId;
    }
}