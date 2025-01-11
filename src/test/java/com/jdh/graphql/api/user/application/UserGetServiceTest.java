package com.jdh.graphql.api.user.application;

import com.jdh.graphql.api.user.application.impl.UserGetServiceImpl;
import com.jdh.graphql.api.user.domain.entity.User;
import com.jdh.graphql.api.user.domain.entity.value.UserInfo;
import com.jdh.graphql.api.user.domain.repository.UserRepository;
import com.jdh.graphql.api.user.dto.request.UserGetRequestDTO;
import com.jdh.graphql.api.user.dto.response.UserGetResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserGetServiceTest {

    @InjectMocks
    UserGetServiceImpl target;

    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("UserGetServiceImpl이 null인지 체크")
    void userGetServiceImpl_null_check() {
        assertThat(target).isNotNull();
    }

    @Test
    @DisplayName("사용자 동적 조회 테스트")
    void userGetServiceImpl_user_dynamic_select_check() {
        // given
        final User user = getUser(getUserInfoA());
        final List<User> userList = new ArrayList<>() {{
            add(user);
        }};
        when(userRepository.findUserByAttributes(null, "AAA", null)).thenReturn(userList);

        // request dto given
        final UserGetRequestDTO requestDTO = mock(UserGetRequestDTO.class);
        when(requestDTO.getId()).thenReturn(null);
        when(requestDTO.getName()).thenReturn("AAA");
        when(requestDTO.getAge()).thenReturn(null);

        // when
        final List<UserGetResponseDTO> result = target.getUser(requestDTO);

        // then
        assertThat(result.get(0).name()).isEqualTo("AAA");
        assertThat(result.get(0).age()).isEqualTo(20);

        // verify
        verify(userRepository, times(1)).findUserByAttributes(null, "AAA", null);
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

}
