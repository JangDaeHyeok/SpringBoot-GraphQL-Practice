package com.jdh.graphql.api.board.domain.repository;

import com.jdh.graphql.api.board.domain.entity.Board;

import java.util.List;

public interface BoardCustomRepository {

    List<Board> findBoardByAttributes(Long id, Long userId, String title, String contents);

}
