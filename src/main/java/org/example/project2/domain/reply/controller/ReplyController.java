package org.example.project2.domain.reply.controller;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.reply.dto.response.ReplyResponseDto;
import org.example.project2.domain.reply.service.ReplyService;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService itemService;

    @GetMapping("/item/{itemId}")
    public ResponseEntity<ResponseDTO<List<ReplyResponseDto>>> getAllReplies(
            @PathVariable long itemId) {

        ResponseDTO<List<ReplyResponseDto>>
                response = itemService.getAllReplies(itemId);

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }
}