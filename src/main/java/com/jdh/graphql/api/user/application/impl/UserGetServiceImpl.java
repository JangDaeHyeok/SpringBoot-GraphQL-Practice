package com.jdh.graphql.api.user.application.impl;

import com.jdh.graphql.api.user.application.UserGetService;
import com.jdh.graphql.api.user.domain.repository.UserRepository;
import com.jdh.graphql.api.user.dto.request.UserGetRequestDTO;
import com.jdh.graphql.api.user.dto.response.UserGetResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserGetServiceImpl implements UserGetService {

    private final UserRepository userRepository;

    @Override
    public List<UserGetResponseDTO> getUser(UserGetRequestDTO requestDTO) {
        return userRepository.findUserByAttributes(requestDTO.getId(), requestDTO.getName(), requestDTO.getAge())
                .stream().map(UserGetResponseDTO::of)
                .toList();
    }
}
