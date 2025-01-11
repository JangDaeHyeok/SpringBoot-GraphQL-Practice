package com.jdh.graphql.api.user.application;

import com.jdh.graphql.api.user.dto.request.UserGetRequestDTO;
import com.jdh.graphql.api.user.dto.response.UserGetResponseDTO;

import java.util.List;

public interface UserGetService {

    List<UserGetResponseDTO> getUser(UserGetRequestDTO requestDTO);

}
