package com.jdh.graphql.api.board.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class BoardAddRequestDTO {

    @NotNull
    @Min(1)
    private Long userId;

    @NotEmpty
    private String title;

    @NotEmpty
    private String contents;

}
