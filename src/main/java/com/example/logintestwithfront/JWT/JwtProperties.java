package com.example.logintestwithfront.JWT;

public interface JwtProperties {
    String SECRET = "PLANet"; // Signatuer를 해싱할 때 사용되는 비밀 키
    int EXPIRATION_TIME =  864000000; // 토큰 만료 기간(10일)
    String TOKEN_PREFIX = "Bearer "; // 토큰 앞에 붙는 정해진 형식 (공백 필수)
    String HEADER_STRING = "Authorization"; // 헤더의 Authorization 항목에 토큰을 넣는다.
    String USER_ID = "UserId"; // 헤더의 UserId 항목에 토큰을 넣는다.
}