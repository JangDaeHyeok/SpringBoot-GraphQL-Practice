package com.jdh.graphql.api.board.application;

import com.jdh.graphql.api.board.application.impl.BoardGetServiceImpl;
import com.jdh.graphql.api.board.domain.entity.Board;
import com.jdh.graphql.api.board.domain.entity.value.BoardInfo;
import com.jdh.graphql.api.board.domain.entity.value.UserId;
import com.jdh.graphql.api.board.domain.repository.BoardRepository;
import com.jdh.graphql.api.board.dto.request.BoardGetRequestDTO;
import com.jdh.graphql.api.board.dto.response.BoardGetResponseDTO;
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
class BoardGetServiceTest {

    @InjectMocks
    BoardGetServiceImpl target;

    @Mock
    BoardRepository boardRepository;

    @Test
    @DisplayName("BoardGetServiceImpl이 null인지 체크")
    void boardGetServiceImpl_null_check() {
        assertThat(target).isNotNull();
    }

    @Test
    @DisplayName("사용자 동적 조회 테스트")
    void userGetServiceImpl_user_dynamic_select_check() {
        // given
        final Board boardA = getBoard(getUserIdOne(), getBoardInfoA());
        final List<Board> boardList = new ArrayList<>() {{
            add(boardA);
        }};
        when(boardRepository.findBoardByAttributes(null, null, "A", null)).thenReturn(boardList);

        // request dto given
        final BoardGetRequestDTO requestDTO = mock(BoardGetRequestDTO.class);
        when(requestDTO.getId()).thenReturn(null);
        when(requestDTO.getUserId()).thenReturn(null);
        when(requestDTO.getTitle()).thenReturn("A");
        when(requestDTO.getContents()).thenReturn(null);

        // when
        final List<BoardGetResponseDTO> result = target.getBoard(requestDTO);

        // then
        assertThat(result.get(0).userId()).isEqualTo(1);
        assertThat(result.get(0).title()).isEqualTo("A");
        assertThat(result.get(0).contents()).isEqualTo("AAA");

        // verify
        verify(boardRepository, times(1)).findBoardByAttributes(null, null, "A", null);
    }

    /**
     * 테스트 사용자 Entity 생성
     */
    private Board getBoard(UserId userId, BoardInfo boardInfo) {
        return Board.builder()
                .userId(userId)
                .boardInfo(boardInfo)
                .build();
    }

    /**
     * 1 테스트 사용자 id 생성
     */
    private UserId getUserIdOne() {
        return UserId.builder()
                .userId(1)
                .build();
    }

    /**
     * AAA 테스트 게시물 정보 생성
     */
    private BoardInfo getBoardInfoA() {
        return BoardInfo.builder()
                .title("A")
                .contents("AAA")
                .build();
    }

}
