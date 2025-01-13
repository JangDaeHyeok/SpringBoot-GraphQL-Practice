package com.jdh.graphql.api.board.dto.response;

import com.jdh.graphql.api.board.domain.entity.Board;
import lombok.Builder;

@Builder
public record BoardGetResponseDTO(Long id, Long userId, String title, String contents) {

    public static BoardGetResponseDTO of(Board board) {
        return BoardGetResponseDTO.builder()
                .id(board.getId())
                .userId(board.getUserId().getUserId())
                .title(board.getBoardInfo().getTitle())
                .contents(board.getBoardInfo().getContents())
                .build();
    }

}
