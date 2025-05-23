package org.example.project2.domain.member.dto.request;

import jakarta.validation.constraints.NotNull;

public record LoginRequestDto(
    @NotNull(message = "email은 필수값입니다")
    String email,
    @NotNull(message = "password은 필수값입니다")
    String password
) {

}
