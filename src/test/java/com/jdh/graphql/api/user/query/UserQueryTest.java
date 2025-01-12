package com.jdh.graphql.api.user.query;

import com.jdh.graphql.api.user.domain.entity.User;
import com.jdh.graphql.api.user.domain.entity.value.UserInfo;
import com.jdh.graphql.api.user.domain.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.transaction.annotation.Transactional;

@GraphQlTest
@ComponentScan(basePackages = "com.jdh.graphql")
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
class UserQueryTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void addUser() {
        final User userA = getUser(getUserInfoA());
        final User userB = getUser(getUserInfoB());

        userRepository.save(userA);
        userRepository.save(userB);
    }

    @AfterEach
    public void removeUser() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("User 사용자 이름으로 단건 조회 테스트")
    void getUser_name_단건_조회_쿼리_테스트() {
        graphQlTester.documentName("getUser")
                .variable("name", "AAA")
                .execute()
                .path("getUsers[0].id")
                .entity(Long.class)
                .isEqualTo(1L)
                .path("getUsers[0].name")
                .entity(String.class)
                .isEqualTo("AAA")
                .path("getUsers[0].age")
                .entity(Integer.class)
                .isEqualTo(20);
    }

    @Test
    @DisplayName("User 목록 조회 테스트")
    void getUser_목록_조회_쿼리_테스트() {
        graphQlTester.documentName("getUsers")
                .execute()
                .path("getUsers[*].name")
                .entityList(String.class)
                .containsExactly("AAA", "BBB")
                .path("getUsers[*].age")
                .entityList(Integer.class)
                .containsExactly(20, 30);
    }

    @Test
    @DisplayName("User 등록 테스트")
    void addUser_등록_테스트() {
        graphQlTester.documentName("addUser")
                .variable("name", "AAA")
                .variable("age", 20)
                .execute()
                .path("addUser.name")
                .entity(String.class)
                .isEqualTo("AAA")
                .path("addUser.age")
                .entity(Integer.class)
                .isEqualTo(20);
    }

    /**
     * 테스트 사용자 Entity 생성
     */
    private User getUser(UserInfo userInfo) {
        return User.builder()
                .userInfo(userInfo)
                .build();
    }

    /**
     * AAA 테스트 사용자 정보 생성
     */
    private UserInfo getUserInfoA() {
        return UserInfo.builder()
                .name("AAA")
                .age(20)
                .build();
    }

    /**
     * BBB 테스트 사용자 정보 생성
     */
    private UserInfo getUserInfoB() {
        return UserInfo.builder()
                .name("BBB")
                .age(30)
                .build();
    }

}
