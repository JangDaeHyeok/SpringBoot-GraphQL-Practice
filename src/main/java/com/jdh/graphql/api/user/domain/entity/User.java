package com.jdh.graphql.api.user.domain.entity;

import com.jdh.graphql.api.user.domain.entity.value.UserInfo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_info")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private UserInfo userInfo;

    public void changeUserName(String newName) {
        this.userInfo = UserInfo.builder()
                .name(newName)
                .age(this.userInfo.getAge())
                .build();
    }

}
