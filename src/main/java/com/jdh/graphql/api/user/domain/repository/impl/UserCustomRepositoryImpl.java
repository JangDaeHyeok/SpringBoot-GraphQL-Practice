package com.jdh.graphql.api.user.domain.repository.impl;

import com.jdh.graphql.api.user.domain.entity.User;
import com.jdh.graphql.api.user.domain.repository.UserCustomRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jdh.graphql.api.user.domain.entity.QUser.user;

@RequiredArgsConstructor
@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<User> findUserByAttributes(Long id, String name, Integer age) {
        BooleanBuilder builder = getBooleanBuilder(id, name, age);

        return jpaQueryFactory
                .selectFrom(user)
                .where(builder)
                .fetch();
    }

    private BooleanBuilder getBooleanBuilder(Long id, String name, Integer age) {
        BooleanBuilder builder = new BooleanBuilder();

        if (id != null) {
            builder.and(user.id.eq(id));
        }
        if (name != null) {
            builder.and(user.userInfo.name.eq(name));
        }
        if (age != null) {
            builder.and(user.userInfo.age.eq(age));
        }

        return builder;
    }

}
