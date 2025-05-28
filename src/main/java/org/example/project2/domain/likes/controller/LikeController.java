package org.example.project2.domain.likes.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project2.domain.likes.dto.request.PostLikeRequestDto;
import org.example.project2.domain.likes.service.LikeService;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("")
    public ResponseEntity<ResponseDTO<Void>> postLike(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @Valid @RequestBody PostLikeRequestDto postItemRequestDto) {

        ResponseDTO<Void> response = likeService.postLike(principalDetails, postItemRequestDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}