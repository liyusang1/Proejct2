package org.example.project2.domain.freeBoards.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project2.domain.freeBoards.dto.request.PostFreeBoardReplyRequestDto;
import org.example.project2.domain.freeBoards.dto.response.FreeBoardReplyResponseDto;
import org.example.project2.domain.freeBoards.service.FreeBoardReplyService;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reply/free-board")
@RequiredArgsConstructor
public class FreeBoardReplyController {

    private final FreeBoardReplyService freeBoardReplyService;

    @GetMapping("/{boardId}")
    public ResponseEntity<ResponseDTO<List<FreeBoardReplyResponseDto>>> getAllReplies(
            @PathVariable long boardId) {
        ResponseDTO<List<FreeBoardReplyResponseDto>> response = freeBoardReplyService.getAllReplies(boardId);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDTO<Void>> postReplies(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @Valid @RequestBody PostFreeBoardReplyRequestDto dto) {
        ResponseDTO<Void> response = freeBoardReplyService.postReplies(principalDetails, dto);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<ResponseDTO<Void>> deleteReplies(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable long replyId) {
        ResponseDTO<Void> response = freeBoardReplyService.deleteReplies(principalDetails, replyId);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}