package com.example.logintestwithfront.Repository;

import com.example.logintestwithfront.Model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByKakaoEmail(String kakaoEmail);
    User findByUserCode(String userCode);

    // 이미 가입된 계정인지 찾기
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query(" select u from User u where u.kakaoEmail = :user_id")
    Optional<User> findByEmail(@Param("user_id") String user_id);
}
