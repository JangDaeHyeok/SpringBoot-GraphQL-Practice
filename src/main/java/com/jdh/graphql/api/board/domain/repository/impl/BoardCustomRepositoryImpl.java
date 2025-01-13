package com.jdh.graphql.api.board.domain.repository.impl;

import com.jdh.graphql.api.board.domain.entity.Board;
import com.jdh.graphql.api.board.domain.repository.BoardCustomRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jdh.graphql.api.board.domain.entity.QBoard.board;

@RequiredArgsConstructor
@Repository
public class BoardCustomRepositoryImpl implements BoardCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Board> findBoardByAttributes(Long id, Long userId, String title, String contents) {
        BooleanBuilder builder = getBooleanBuilder(id, userId, title, contents);

        return jpaQueryFactory
                .selectFrom(board)
                .where(builder)
                .fetch();
    }

    private BooleanBuilder getBooleanBuilder(Long id, Long userId, String title, String contents) {
        BooleanBuilder builder = new BooleanBuilder();

        if (id != null) {
            builder.and(board.id.eq(id));
        }
        if (userId != null) {
            builder.and(board.userId.userId.eq(userId));
        }
        if (title != null) {
            builder.and(board.boardInfo.title.eq(title));
        }
        if (contents != null) {
            builder.and(board.boardInfo.contents.eq(contents));
        }

        return builder;
    }

}
