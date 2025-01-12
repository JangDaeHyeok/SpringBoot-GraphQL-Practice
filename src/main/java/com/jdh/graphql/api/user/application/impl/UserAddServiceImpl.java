package com.jdh.graphql.api.user.application.impl;

import com.jdh.graphql.api.user.application.UserAddService;
import com.jdh.graphql.api.user.domain.entity.User;
import com.jdh.graphql.api.user.domain.repository.UserRepository;
import com.jdh.graphql.api.user.dto.request.UserAddRequestDTO;
import com.jdh.graphql.api.user.dto.response.UserGetResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class UserAddServiceImpl implements UserAddService {

    private final UserRepository userRepository;

    @Override
    public UserGetResponseDTO addUser(UserAddRequestDTO requestDTO) {
        User user = User.of(requestDTO);

        userRepository.save(user);

        return UserGetResponseDTO.of(user);
    }
}
