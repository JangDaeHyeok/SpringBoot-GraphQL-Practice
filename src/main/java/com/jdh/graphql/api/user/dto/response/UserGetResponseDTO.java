package com.jdh.graphql.api.user.dto.response;

import com.jdh.graphql.api.user.domain.entity.User;
import lombok.Builder;

@Builder
public record UserGetResponseDTO(Long id, String name, Integer age) {

    public static UserGetResponseDTO of(User user) {
        return UserGetResponseDTO.builder()
                .id(user.getId())
                .name(user.getUserInfo().getName())
                .age(user.getUserInfo().getAge())
                .build();
    }

}
