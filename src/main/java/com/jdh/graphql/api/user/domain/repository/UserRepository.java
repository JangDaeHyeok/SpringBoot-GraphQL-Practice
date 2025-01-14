package com.jdh.graphql.api.user.domain.repository;

import com.jdh.graphql.api.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {
}
