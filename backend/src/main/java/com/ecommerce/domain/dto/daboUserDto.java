package com.ecommerce.domain.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class daboUserDto {

    String bloodType;

    String email;

    String password;

    String nickname;
}
