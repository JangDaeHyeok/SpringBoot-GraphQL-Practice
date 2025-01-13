package com.jdh.graphql.api.board.domain.entity;

import com.jdh.graphql.api.board.domain.entity.value.BoardInfo;
import com.jdh.graphql.api.board.domain.entity.value.UserId;
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

}
