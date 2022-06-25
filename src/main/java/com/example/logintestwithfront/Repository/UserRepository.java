package com.example.logintestwithfront.Repository;

import com.example.logintestwithfront.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByKakaoEmail(String kakaoEmail);
    User findByUserCode(String userCode);
}
