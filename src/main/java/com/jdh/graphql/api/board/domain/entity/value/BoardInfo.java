package com.jdh.graphql.api.board.domain.entity.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Embeddable
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardInfo {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

}
