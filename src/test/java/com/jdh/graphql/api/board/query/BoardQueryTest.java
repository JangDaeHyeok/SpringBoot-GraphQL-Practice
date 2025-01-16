package com.jdh.graphql.api.board.query;

import com.jdh.graphql.api.board.domain.entity.Board;
import com.jdh.graphql.api.board.domain.entity.value.BoardInfo;
import com.jdh.graphql.api.board.domain.entity.value.UserId;
import com.jdh.graphql.api.board.domain.repository.BoardRepository;
import com.jdh.graphql.api.board.dto.request.BoardAddRequestDTO;
import com.jdh.graphql.api.board.dto.request.BoardGetRequestDTO;
import com.jdh.graphql.api.user.domain.entity.User;
import com.jdh.graphql.api.user.domain.entity.value.UserInfo;
import com.jdh.graphql.api.user.domain.repository.UserRepository;
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
class BoardQueryTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @BeforeAll
    public void addBoard() {
        userRepository.deleteAll();
        boardRepository.deleteAll();

        final User userA = getUser(getUserInfoA());
        final Board boardA = getBoard(getUserIdOne(), getBoardInfoA());
        final Board boardB = getBoard(getUserIdTwo(), getBoardInfoB());

        userRepository.save(userA);
        boardRepository.save(boardA);
        boardRepository.save(boardB);
    }

    @AfterAll
    public void removeBoard() {
        boardRepository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("Board 게시물 제목으로 단건 조회 테스트")
    void getBoard_title_단건_조회_쿼리_테스트() {
        final BoardGetRequestDTO request = new BoardGetRequestDTO(null, null, "B", null);

        graphQlTester.documentName("getBoard")
                .variable("request", request)
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
    @Order(2)
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

    @Test
    @Order(3)
    @DisplayName("Board 등록 테스트")
    void addBoard_등록_테스트() {
        final BoardAddRequestDTO request = new BoardAddRequestDTO(1L, "test title", "test contents");

        graphQlTester.documentName("addBoard")
                .variable("request", request)
                .execute()
                .path("addBoard.userId")
                .entity(Integer.class)
                .isEqualTo(1)
                .path("addBoard.title")
                .entity(String.class)
                .isEqualTo("test title")
                .path("addBoard.contents")
                .entity(String.class)
                .isEqualTo("test contents");
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
     * 테스트 게시물 Entity 생성
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
