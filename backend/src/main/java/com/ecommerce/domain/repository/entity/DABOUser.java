package com.ecommerce.domain.repository.entity;

import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class DABOUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    @Column(name = "nickname",unique = true, nullable = false)
    String nickname;

    String bloodType;

    @Column(name = "email",unique = true, nullable = false)
    String email;

    String password;

    Long point;
}
