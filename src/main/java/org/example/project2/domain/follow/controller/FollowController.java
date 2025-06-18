package org.example.project2.domain.follow.controller;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.follow.dto.response.FollowerResponseDto;
import org.example.project2.domain.follow.dto.response.FollowingResponseDto;
import org.example.project2.domain.follow.dto.response.GetIsFollowingResponseDto;
import org.example.project2.domain.follow.service.FollowService;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{memberId}")
    public ResponseEntity<ResponseDTO<Void>> postFollow(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long memberId) {

        ResponseDTO<Void> response = followService.postFollow(
                principalDetails,
                memberId);

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

    @GetMapping("/follower/{memberId}")
    public ResponseEntity<ResponseDTO<List<FollowerResponseDto>>> getFollower(
            @PathVariable Long memberId) {

        ResponseDTO<List<FollowerResponseDto>> response =
                followService.getFollower(memberId);

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

    @GetMapping("/following/{memberId}")
    public ResponseEntity<ResponseDTO<List<FollowingResponseDto>>> getFollowing(
            @PathVariable Long memberId) {

        ResponseDTO<List<FollowingResponseDto>> response =
                followService.getFollowing(memberId);

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

    @GetMapping("/is-following/{memberId}")
    public ResponseEntity<ResponseDTO<GetIsFollowingResponseDto>> getIsFollowing(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long memberId) {

        ResponseDTO<GetIsFollowingResponseDto> response =
                followService.getIsFollowing(principalDetails,memberId);

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }
}