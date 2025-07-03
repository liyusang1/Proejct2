package org.example.project2.domain.recipes.controller; // 패키지 경로 수정

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.domain.member.exception.UserNotFoundException;
import org.example.project2.domain.recipes.dto.request.BookmarkRequestDto;
import org.example.project2.domain.recipes.dto.response.BookmarkResponseDto;
import org.example.project2.domain.recipes.service.BookmarkService;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity; // ResponseEntity 사용
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    public ResponseEntity<ResponseDTO<Void>> addOrActivateBookmark(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody BookmarkRequestDto dto) {
        // 서비스 계층 호출
        bookmarkService.saveBookmark(principalDetails, dto);
        // 성공 응답 (HTTP 200 OK) 반환
        return ResponseEntity.ok(ResponseDTO.ok());
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<ResponseDTO<Void>> deactivateBookmark(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable("recipeId") Long recipeId) {
        // 서비스 계층 호출
        bookmarkService.deleteBookmark(principalDetails, recipeId);
        // 성공 응답 (HTTP 200 OK) 반환
        return ResponseEntity.ok(ResponseDTO.ok());
    }

    @GetMapping("/{recipeId}/status")
    public ResponseEntity<ResponseDTO<Map<String, Boolean>>> getBookmarkStatus(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long recipeId) {
        boolean isBookmarked = bookmarkService.getBookmarkStatus(principalDetails, recipeId);
        Map<String, Boolean> data = new HashMap<>();
        data.put("isBookmarked", isBookmarked);
        // data를 포함하는 okWithData 메서드를 사용
        return ResponseEntity.ok(ResponseDTO.okWithData(data));
    }

    // 로그인한 사용자의 북마크된 레시피 목록 조회
    @GetMapping
    public ResponseEntity<ResponseDTO<Page<BookmarkResponseDto>>> getMyBookmarks(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        if (principalDetails == null || principalDetails.getMember() == null) { // Member null 체크 추가
            throw new UserNotFoundException();
        }
        Member member = principalDetails.getMember();
        ResponseDTO<Page<BookmarkResponseDto>> responses = bookmarkService.getMyBookmarks(member.getId(), pageable);
        return ResponseEntity
                .status(responses.getCode())
                .body(responses);
    }
}