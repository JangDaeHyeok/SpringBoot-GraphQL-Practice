package com.jdh.graphql.api.user.controller;

import com.jdh.graphql.api.user.application.UserAddService;
import com.jdh.graphql.api.user.application.UserGetService;
import com.jdh.graphql.api.user.dto.request.UserAddRequestDTO;
import com.jdh.graphql.api.user.dto.request.UserGetRequestDTO;
import com.jdh.graphql.api.user.dto.response.UserGetResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class UserController {

    private final UserGetService userGetService;

    private final UserAddService userAddService;

    @QueryMapping
    public List<UserGetResponseDTO> getUsers(@Argument UserGetRequestDTO request) {
        log.info(request.toString());

        return userGetService.getUser(request);
    }

    @MutationMapping
    public UserGetResponseDTO addUser(@Argument @Valid UserAddRequestDTO request) {
        log.info(request.toString());

        return userAddService.addUser(request);
    }

}
