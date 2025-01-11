package com.jdh.graphql.api.user.domain.repository;

import com.jdh.graphql.api.user.domain.entity.User;
import com.jdh.graphql.api.user.domain.entity.value.UserInfo;
import com.jdh.graphql.config.querydsl.QueryDslConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QueryDslConfig.class)
class UserRepositoryTest {

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
    @DisplayName("UserRepository가 null인지 체크")
    void userRepository_null_check() {
        assertThat(userRepository).isNotNull();
    }

    @Test
    @DisplayName("UserRepository 사용자 목록 전체 조회 테스트")
    void userRepository_select_all_test() {
        // when
        final List<User> users = userRepository.findAll();
        final User userA = users.get(0);
        final User userB = users.get(1);

        // then
        assertThat(userA.getUserInfo().getName()).isEqualTo("AAA");
        assertThat(userA.getUserInfo().getAge()).isEqualTo(20);
        assertThat(userB.getUserInfo().getName()).isEqualTo("BBB");
        assertThat(userB.getUserInfo().getAge()).isEqualTo(30);
    }

    @Test
    @DisplayName("UserRepository findUserByAttributes 조회 custom 메소드 테스트")
    void userRepository_findUserByAttributes_test() {
        // when
        final User userA = userRepository.findUserByAttributes(null, "AAA", null).get(0);
        final User userB = userRepository.findUserByAttributes(null, null, 30).get(0);

        // then
        assertThat(userA.getUserInfo().getName()).isEqualTo("AAA");
        assertThat(userA.getUserInfo().getAge()).isEqualTo(20);
        assertThat(userB.getUserInfo().getName()).isEqualTo("BBB");
        assertThat(userB.getUserInfo().getAge()).isEqualTo(30);
    }

    @Test
    @DisplayName("UserRepository save 테스트")
    void userRepository_save_test() {
        // given
        final User userC = getUser(getUserInfoC());
        userRepository.save(userC);

        // when
        final User fineUserC = userRepository.findUserByAttributes(null, "CCC", null).get(0);

        // then
        assertThat(fineUserC.getUserInfo().getName()).isEqualTo("CCC");
        assertThat(fineUserC.getUserInfo().getAge()).isEqualTo(40);
    }

    @Test
    @DisplayName("UserRepository update 테스트")
    void userRepository_update_test() {
        // given
        final User user = userRepository.findUserByAttributes(null, "AAA", null).get(0);
        user.changeUserName("DDD");
        userRepository.save(user);

        // when
        final User fineUser = userRepository.findUserByAttributes(null, "DDD", null).get(0);

        // then
        assertThat(fineUser.getUserInfo().getName()).isEqualTo("DDD");
        assertThat(fineUser.getUserInfo().getAge()).isEqualTo(20);
    }

    @Test
    @DisplayName("UserRepository delete 테스트")
    void userRepository_delete_test() {
        // given
        final User user = userRepository.findUserByAttributes(null, "AAA", null).get(0);
        userRepository.delete(user);

        // when
        final List<User> fineUser = userRepository.findUserByAttributes(null, "AAA", null);

        // then
        assertThat(fineUser).isNullOrEmpty();
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

    /**
     * CCC 테스트 사용자 정보 생성
     */
    private UserInfo getUserInfoC() {
        return UserInfo.builder()
                .name("CCC")
                .age(40)
                .build();
    }

}
