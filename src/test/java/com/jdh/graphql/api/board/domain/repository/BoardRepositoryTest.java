package com.jdh.graphql.api.board.domain.repository;

import com.jdh.graphql.api.board.domain.entity.Board;
import com.jdh.graphql.api.board.domain.entity.value.BoardInfo;
import com.jdh.graphql.api.board.domain.entity.value.UserId;
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
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    public void addBoard() {
        final Board boardA = getBoard(getUserIdOne(), getBoardInfoA());
        final Board boardB = getBoard(getUserIdTwo(), getBoardInfoB());

        boardRepository.save(boardA);
        boardRepository.save(boardB);
    }

    @AfterEach
    public void removeBoard() {
        boardRepository.deleteAll();
    }

    @Test
    @DisplayName("boardRepository가 null인지 체크")
    void boardRepository_null_check() {
        assertThat(boardRepository).isNotNull();
    }

    @Test
    @DisplayName("boardRepository 게시물 목록 전체 조회 테스트")
    void boardRepository_select_all_test() {
        // when
        final List<Board> boards = boardRepository.findAll();
        final Board boardA = boards.get(0);
        final Board boardB = boards.get(1);

        // then
        assertThat(boardA.getUserId().getUserId()).isEqualTo(1);
        assertThat(boardA.getBoardInfo().getTitle()).isEqualTo("A");
        assertThat(boardA.getBoardInfo().getContents()).isEqualTo("AAA");
        assertThat(boardB.getUserId().getUserId()).isEqualTo(2);
        assertThat(boardB.getBoardInfo().getTitle()).isEqualTo("B");
        assertThat(boardB.getBoardInfo().getContents()).isEqualTo("BBB");
    }

    @Test
    @DisplayName("boardRepository findBoardByAttributes 조회 custom 메소드 테스트")
    void boardRepository_findBoardByAttributes_test() {
        // when
        final Board boardA = boardRepository.findBoardByAttributes(null, 1L, null, null).get(0);
        final Board boardB = boardRepository.findBoardByAttributes(null, null, "B", null).get(0);
        final Board boardB_2 = boardRepository.findBoardByAttributes(null, null, null, "BBB").get(0);

        // then
        assertThat(boardA.getUserId().getUserId()).isEqualTo(1);
        assertThat(boardA.getBoardInfo().getTitle()).isEqualTo("A");
        assertThat(boardA.getBoardInfo().getContents()).isEqualTo("AAA");
        assertThat(boardB.getUserId().getUserId()).isEqualTo(2);
        assertThat(boardB.getBoardInfo().getTitle()).isEqualTo("B");
        assertThat(boardB.getBoardInfo().getContents()).isEqualTo("BBB");
        assertThat(boardB_2.getUserId().getUserId()).isEqualTo(2);
        assertThat(boardB_2.getBoardInfo().getTitle()).isEqualTo("B");
        assertThat(boardB_2.getBoardInfo().getContents()).isEqualTo("BBB");
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
     * 2 테스트 사용자 id 생성
     */
    private UserId getUserIdTwo() {
        return UserId.builder()
                .userId(2)
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

    /**
     * BBB 테스트 게시물 정보 생성
     */
    private BoardInfo getBoardInfoB() {
        return BoardInfo.builder()
                .title("B")
                .contents("BBB")
                .build();
    }

}
