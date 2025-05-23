package org.example.project2.domain.member.dto.request;

public record PasswordRequestDto(
    String currentPassword,

    String newPassword,

    String confirmPassword
) {}