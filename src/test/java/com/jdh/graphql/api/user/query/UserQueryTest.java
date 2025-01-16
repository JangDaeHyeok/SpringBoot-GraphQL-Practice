package com.jdh.graphql.api.user.query;

import com.jdh.graphql.api.user.domain.entity.User;
import com.jdh.graphql.api.user.domain.entity.value.UserInfo;
import com.jdh.graphql.api.user.domain.repository.UserRepository;
import com.jdh.graphql.api.user.dto.request.UserAddRequestDTO;
import com.jdh.graphql.api.user.dto.request.UserGetRequestDTO;
import org.junit.jupiter.api.*;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class UserQueryTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    public void addUser() {
        userRepository.deleteAll();

        final User userA = getUser(getUserInfoA());
        final User userB = getUser(getUserInfoB());

        userRepository.save(userA);
        userRepository.save(userB);
    }

    @AfterAll
    public void removeUser() {
        userRepository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("User 사용자 이름으로 단건 조회 테스트")
    void getUser_name_단건_조회_쿼리_테스트() {
        final UserGetRequestDTO request = new UserGetRequestDTO(null, "AAA", null);

        graphQlTester.documentName("getUser")
                .variable("request", request)
                .execute()
                .path("getUsers[0].name")
                .entity(String.class)
                .isEqualTo("AAA")
                .path("getUsers[0].age")
                .entity(Integer.class)
                .isEqualTo(20);
    }

    @Test
    @Order(2)
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
    @Order(3)
    @DisplayName("User 등록 테스트")
    void addUser_등록_테스트() {
        final UserAddRequestDTO request = new UserAddRequestDTO("AAA", 20);

        graphQlTester.documentName("addUser")
                .variable("request", request)
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
