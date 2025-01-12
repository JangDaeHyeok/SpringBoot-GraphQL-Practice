package com.jdh.graphql.api.user.application;

import com.jdh.graphql.api.user.dto.request.UserAddRequestDTO;
import com.jdh.graphql.api.user.dto.response.UserGetResponseDTO;

public interface UserAddService {

    UserGetResponseDTO addUser(UserAddRequestDTO requestDTO);

}
