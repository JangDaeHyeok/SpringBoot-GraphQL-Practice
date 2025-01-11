package com.jdh.graphql.api.user.domain.repository;

import com.jdh.graphql.api.user.domain.entity.User;

import java.util.List;

public interface UserCustomRepository {

    List<User> findUserByAttributes(Long id, String name, Integer age);

}
