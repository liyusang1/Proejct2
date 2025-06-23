package org.example.project2.domain.freeBoards.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project2.domain.freeBoards.dto.request.PostFreeBoardRequestDto;
import org.example.project2.domain.freeBoards.dto.response.FreeBoardResponseDto;
import org.example.project2.domain.freeBoards.service.FreeBoardLikeService;
import org.example.project2.domain.freeBoards.service.FreeBoardService;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/free-boards")
@RequiredArgsConstructor
public class FreeBoardsController {

    private final FreeBoardService freeBoardService;
    private final FreeBoardLikeService freeBoardLikeService;

    // 게시글 목록 (검색/페이징 지원)
    @GetMapping("")
    public ResponseEntity<ResponseDTO<Page<FreeBoardResponseDto>>> getAllBoards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        ResponseDTO<Page<FreeBoardResponseDto>> response = freeBoardService.getAllBoards(pageable, search, category);
        return ResponseEntity.status(response.getCode()).body(response);
    }


    // 게시글 상세 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<ResponseDTO<FreeBoardResponseDto>> getBoardDetail(@PathVariable long boardId, @AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletRequest request) {
        Member member = (principalDetails != null) ? principalDetails.getMember() : null;
        ResponseDTO<FreeBoardResponseDto> response = freeBoardService.getBoardDetail(boardId, request, member);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    // 게시글 등록
    @PostMapping("")
    public ResponseEntity<ResponseDTO<Void>> postBoard(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @Valid @RequestBody PostFreeBoardRequestDto requestDto
    ) {
        ResponseDTO<Void> response = freeBoardService.postBoard(principalDetails, requestDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    // 게시글 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<ResponseDTO<Void>> deleteBoard(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable long boardId
    ) {
        ResponseDTO<Void> response = freeBoardService.deleteBoard(principalDetails, boardId);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    // 게시글 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<ResponseDTO<Void>> updateBoard(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable long boardId,
            @Valid @RequestBody PostFreeBoardRequestDto requestDto
    ) {
        ResponseDTO<Void> response = freeBoardService.updateBoard(principalDetails, boardId, requestDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }
    // 추천
    @PostMapping("/{id}/like")
    public ResponseEntity<ResponseDTO<Void>> likeBoard(
            @PathVariable Long id,
            @AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity.ok(
                freeBoardLikeService.vote(id, principal.getMember(), true)
        );
    }

    // 비추천
    @PostMapping("/{id}/dislike")
    public ResponseEntity<ResponseDTO<Void>> dislikeBoard(
            @PathVariable Long id,
            @AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity.ok(
                freeBoardLikeService.vote(id, principal.getMember(), false)
        );
    }

    // 내가 누른 내역
    @GetMapping("/{id}/my-vote")
    public ResponseEntity<ResponseDTO<Boolean>> getMyVote(
            @PathVariable Long id,
            @AuthenticationPrincipal PrincipalDetails principal) {
        Optional<Boolean> result = freeBoardLikeService.myVote(id, principal.getMember());
        return ResponseEntity.ok(
                result.map(ResponseDTO::okWithData).orElseGet(() -> ResponseDTO.okWithData(null))
        );
}
}