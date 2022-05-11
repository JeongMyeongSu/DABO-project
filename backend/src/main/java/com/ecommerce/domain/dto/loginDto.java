package com.ecommerce.domain.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class loginDto {
    String email;
    String password;
}
