package com.example.logintestwithfront.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByKakaoEmail(String kakaoEmail);
    User findByUserCode(String userCode);
}
