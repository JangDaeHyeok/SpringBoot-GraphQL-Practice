package com.jdh.graphql.api.board.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class BoardGetRequestDTO {

    private Long id;

    private Long userId;

    private String title;

    private String contents;

}
