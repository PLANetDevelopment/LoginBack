package com.example.logintestwithfront.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.logintestwithfront.JWT.JwtProperties;
import com.example.logintestwithfront.Model.Oauth.KakaoProfile;
import com.example.logintestwithfront.Model.OauthToken;
import com.example.logintestwithfront.Model.User;
import com.example.logintestwithfront.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 프론트의 인가 코드를 통해 access_token 발급
    public OauthToken getAccessToken(String code) {

        // Http 통신에 유용한 클래스
        RestTemplate rt = new RestTemplate();

//        rt.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        rt.setErrorHandler(new DefaultResponseErrorHandler() {
            public boolean hasError(ClientHttpResponse response) throws IOException {
                HttpStatus status = response.getStatusCode();
                return status.series() == HttpStatus.Series.SERVER_ERROR;
            }
        });

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        System.out.println("headers are " + headers);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "30e5410f7f73eeb781a471522f30d2a6");
        params.add("redirect_uri", "http://localhost:3000/oauth");
        params.add("code", code);
        params.add("client_secret", "fm0QlhjRTs8XDMaBjkECXApaTt65NWgV");

        // HttpHeader와 httpBody를 하나의 객체에 담는다.
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // POST 방식으로 http 요청 -> 응답
        // Json 형식의 응답을 String 타입으로 받는다.
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // String으로 받은 Json 형식의 데이터를 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        OauthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(accessTokenResponse.getBody(), OauthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return oauthToken;
    }

    public User saveUser(String token) {

        KakaoProfile profile = findProfile(token);

        User user = userRepository.findByKakaoEmail(profile.getKakao_account().getEmail());

        if(user == null) {
            user = User.builder()
                    .kakaoId(profile.getId())
                    //(4)
                    .kakaoProfileImg(profile.getKakao_account().getProfile().getProfile_image_url())
                    .kakaoNickname(profile.getKakao_account().getProfile().getNickname())
                    .kakaoEmail(profile.getKakao_account().getEmail())
                    //(5)
                    .userRole("ROLE_USER").build();

            userRepository.save(user);
        }

        return user;
    }

    /**
     * access_token으로 카카오 서버에서 사용자 정보를 가져옴
     */
    public KakaoProfile findProfile(String token) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token); //(1-4)
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody 정보를 하나의 객체에 담음
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);

        // Http 요청 (POST 방식) 후, response 변수에 응답을 받음
        ResponseEntity<String> kakaoProfileResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        // Json 응답을 kakaoProfile 객체로 변환해 리턴
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoProfile;
    }

    public String saveUserAndGetToken(String token) { //(1)
        KakaoProfile profile = findProfile(token);

        User user = userRepository.findByKakaoEmail(profile.getKakao_account().getEmail());
        if(user == null) {
            user = User.builder()
                    .kakaoId(profile.getId())
//                    .kakaoProfileImg(profile.getKakao_account().getProfile().getProfile_image_url())
                    .kakaoNickname(profile.getKakao_account().getProfile().getNickname())
                    .kakaoEmail(profile.getKakao_account().getEmail())
                    .userRole("ROLE_USER").build();

            userRepository.save(user);
        }

        return createToken(user); // JWT 토큰 반환
    }

    // String 형의 JWT 토큰 반환
    public String createToken(User user) {

        String jwtToken = JWT.create()

                .withSubject(user.getKakaoEmail())
                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtProperties.EXPIRATION_TIME))

                .withClaim("id", user.getUserCode())
                .withClaim("nickname", user.getKakaoNickname())

                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        return jwtToken;
    }

}