package com.jdh.graphql.api.user.application;

import com.jdh.graphql.api.user.application.impl.UserAddServiceImpl;
import com.jdh.graphql.api.user.domain.entity.User;
import com.jdh.graphql.api.user.domain.repository.UserRepository;
import com.jdh.graphql.api.user.dto.request.UserAddRequestDTO;
import com.jdh.graphql.api.user.dto.response.UserGetResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAddServiceTest {

    @InjectMocks
    UserAddServiceImpl target;

    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("UserAddServiceImpl이 null인지 체크")
    void userAddServiceImpl_null_check() {
        assertThat(target).isNotNull();
    }

    @Test
    @DisplayName("사용자 동적 조회 테스트")
    void userAddServiceImpl_user_add_test() {
        // given

        // request dto given
        final UserAddRequestDTO requestDTO = mock(UserAddRequestDTO.class);
        when(requestDTO.getName()).thenReturn("AAA");
        when(requestDTO.getAge()).thenReturn(20);

        // when
        final UserGetResponseDTO result = target.addUser(requestDTO);

        // then
        assertThat(result.name()).isEqualTo("AAA");
        assertThat(result.age()).isEqualTo(20);

        // verify
        verify(userRepository, times(1)).save(any(User.class));
    }

}
