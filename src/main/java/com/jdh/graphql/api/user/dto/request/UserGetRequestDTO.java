package com.jdh.graphql.api.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class UserGetRequestDTO {

    private Long id;

    private String name;

    private Integer age;

}
