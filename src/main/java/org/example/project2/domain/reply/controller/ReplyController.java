package org.example.project2.domain.reply.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project2.domain.reply.dto.request.PostReplyRequestDto;
import org.example.project2.domain.reply.dto.response.ReplyResponseDto;
import org.example.project2.domain.reply.service.ReplyService;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/item/{itemId}")
    public ResponseEntity<ResponseDTO<List<ReplyResponseDto>>> getAllReplies(
            @PathVariable long itemId) {

        ResponseDTO<List<ReplyResponseDto>>
                response = replyService.getAllReplies(itemId);

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDTO<Void>> postReplies(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @Valid @RequestBody PostReplyRequestDto postReplyRequestDto) {

        ResponseDTO<Void>
                response = replyService.postReplies(principalDetails, postReplyRequestDto);

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<ResponseDTO<Void>> deleteReplies(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable long replyId) {

        ResponseDTO<Void>
                response = replyService.deleteReplies(principalDetails,replyId);

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }
}