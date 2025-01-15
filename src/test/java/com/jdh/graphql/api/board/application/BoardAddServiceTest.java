package com.jdh.graphql.api.board.application;

import com.jdh.graphql.api.board.application.impl.BoardAddServiceImpl;
import com.jdh.graphql.api.board.domain.repository.BoardRepository;
import com.jdh.graphql.api.board.dto.request.BoardAddRequestDTO;
import com.jdh.graphql.api.board.dto.response.BoardGetResponseDTO;
import com.jdh.graphql.api.user.application.UserGetService;
import com.jdh.graphql.api.user.domain.entity.User;
import com.jdh.graphql.api.user.domain.entity.value.UserInfo;
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
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardAddServiceTest {

    @InjectMocks
    BoardAddServiceImpl target;

    @Mock
    BoardRepository boardRepository;

    @Mock
    UserGetService userGetService;

    @Test
    @DisplayName("BoardAddServiceImpl이 null인지 체크")
    void boardAddServiceImpl_null_check() {
        assertThat(target).isNotNull();
    }

    @Test
    @DisplayName("BoardAddServiceImpl 존재하지 않는 사용자 IllegalArgumentException 테스트")
    void boardAddServiceImpl_user_not_exist_test() {
        BoardAddRequestDTO boardAddRequestDTO = getBoardAddRequestDTO();

        // given
        List<UserGetResponseDTO> findUserList = new ArrayList<>();
        when(userGetService.getUser(any(UserGetRequestDTO.class))).thenReturn(findUserList);

        // when
        final Exception result = catchException(() -> target.addBoard(boardAddRequestDTO));

        // then
        assertThat(result)
                .as("Exception should be thrown when user not exist")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("BoardAddServiceImpl 게시글 등록 테스트")
    void boardAddServiceImpl_user_add_test() {
        BoardAddRequestDTO boardAddRequestDTO = getBoardAddRequestDTO();

        // given
        List<UserGetResponseDTO> findUserList = new ArrayList<>() {{
            add(UserGetResponseDTO.of(getUser(getUserInfoA())));
        }};
        when(userGetService.getUser(any(UserGetRequestDTO.class))).thenReturn(findUserList);

        // when
        final BoardGetResponseDTO result = target.addBoard(boardAddRequestDTO);

        // then
        assertThat(result.userId()).isEqualTo(1L);
        assertThat(result.title()).isEqualTo("test title");
        assertThat(result.contents()).isEqualTo("test contents");
    }

    private BoardAddRequestDTO getBoardAddRequestDTO() {
        return new BoardAddRequestDTO(1L, "test title", "test contents");
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
