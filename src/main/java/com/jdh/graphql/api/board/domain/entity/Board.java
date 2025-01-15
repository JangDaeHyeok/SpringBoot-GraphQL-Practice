package com.jdh.graphql.api.board.domain.entity;

import com.jdh.graphql.api.board.domain.entity.value.BoardInfo;
import com.jdh.graphql.api.board.domain.entity.value.UserId;
import com.jdh.graphql.api.board.dto.request.BoardAddRequestDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "table_info", indexes = @Index(name = "idx_user_id", columnList = "user_id"))
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private UserId userId;

    @Embedded
    private BoardInfo boardInfo;

    public static Board of(BoardAddRequestDTO add) {
        UserId addUserId = UserId.builder()
                .userId(add.getUserId())
                .build();

        BoardInfo addBoardInfo = BoardInfo.builder()
                .title(add.getTitle())
                .contents(add.getContents())
                .build();

        return Board.builder()
                .userId(addUserId)
                .boardInfo(addBoardInfo)
                .build();
    }

}
