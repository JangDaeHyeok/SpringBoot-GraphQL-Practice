package com.jdh.graphql.api.board.query;

import com.jdh.graphql.api.board.domain.entity.Board;
import com.jdh.graphql.api.board.domain.entity.value.BoardInfo;
import com.jdh.graphql.api.board.domain.entity.value.UserId;
import com.jdh.graphql.api.board.domain.repository.BoardRepository;
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
class BoardQueryTest {

    @Autowired
    private GraphQlTester graphQlTester;

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
    @DisplayName("Board 게시물 제목으로 단건 조회 테스트")
    void getBoard_title_단건_조회_쿼리_테스트() {
        graphQlTester.documentName("getBoard")
                .variable("title", "B")
                .execute()
                .path("getBoards[0].id")
                .entity(Long.class)
                .isEqualTo(2L)
                .path("getBoards[0].userId")
                .entity(Long.class)
                .isEqualTo(2L)
                .path("getBoards[0].title")
                .entity(String.class)
                .isEqualTo("B")
                .path("getBoards[0].contents")
                .entity(String.class)
                .isEqualTo("BBB");
    }

    @Test
    @DisplayName("Board 목록 조회 테스트")
    void getBoard_목록_조회_쿼리_테스트() {
        graphQlTester.documentName("getBoards")
                .execute()
                .path("getBoards[*].userId")
                .entityList(Long.class)
                .containsExactly(1L, 2L)
                .path("getBoards[*].title")
                .entityList(String.class)
                .containsExactly("A", "B")
                .path("getBoards[*].contents")
                .entityList(String.class)
                .containsExactly("AAA", "BBB");
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
