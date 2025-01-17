package com.jdh.graphql.api.user.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class UserAddRequestDTO {

    @NotBlank
    private String name;

    @NotNull
    @Min(1)
    private Integer age;

}
