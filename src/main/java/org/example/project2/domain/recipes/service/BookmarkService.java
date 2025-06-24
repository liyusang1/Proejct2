package org.example.project2.domain.recipes.service;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.domain.member.exception.UserNotFoundException;
import org.example.project2.domain.member.repository.MemberRepository;
import org.example.project2.domain.recipes.dto.request.BookmarkRequestDto;
import org.example.project2.domain.recipes.dto.response.BookmarkResponseDto;
import org.example.project2.domain.recipes.entity.Bookmark;
import org.example.project2.domain.recipes.entity.Recipes;
import org.example.project2.domain.recipes.exception.BookmarkAlreadyDeactivatedException;
import org.example.project2.domain.recipes.exception.BookmarkAlreadyExistsException;
import org.example.project2.domain.recipes.exception.BookmarkNotFoundException; // 임포트 추가
import org.example.project2.domain.recipes.exception.RecipeIdIsInvalidException;
import org.example.project2.domain.recipes.repository.BookmarkRepository;
import org.example.project2.domain.recipes.repository.RecipesRepository;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final RecipesRepository recipesRepository;
    private final MemberRepository memberRepository;

    public ResponseDTO<Void> saveBookmark(PrincipalDetails principalDetails, BookmarkRequestDto dto) {
        if (principalDetails == null || principalDetails.getMember() == null) {
            throw new UserNotFoundException();
        }
        Member member = principalDetails.getMember();
        Recipes recipe = recipesRepository.findById(dto.recipeId())
                .orElseThrow(RecipeIdIsInvalidException::new);

        Optional<Bookmark> existingBookmark = bookmarkRepository.findByMemberAndRecipe(member, recipe);

        if (existingBookmark.isPresent()) {
            Bookmark bookmark = existingBookmark.get();
            if (bookmark.isStatus()) {
                throw new BookmarkAlreadyExistsException();

            } else {
                bookmark.activate();
                bookmarkRepository.save(bookmark);
                return ResponseDTO.ok();
            }
        } else {
            Bookmark newBookmark = Bookmark.builder()
                    .member(member)
                    .recipe(recipe)
                    .status(true)
                    .build();
            bookmarkRepository.save(newBookmark);
            return ResponseDTO.ok();
        }
    }

    public ResponseDTO<Void> deleteBookmark(PrincipalDetails principalDetails, Long recipeId) {
        if (principalDetails == null || principalDetails.getMember() == null) {
            throw new UserNotFoundException();
        }
        Member member = principalDetails.getMember();
        Recipes recipe = recipesRepository.findById(recipeId)
                .orElseThrow(RecipeIdIsInvalidException::new);

        // 먼저 상태와 관계없이 북마크 자체의 존재 여부를 확인
        Optional<Bookmark> existingBookmark = bookmarkRepository.findByMemberAndRecipe(member, recipe);

        if (existingBookmark.isEmpty()) {
            // 북마크 기록 자체가 없다면
            throw new BookmarkNotFoundException(); // "북마크를 찾을 수 없습니다" 예외 발생
        }

        Bookmark bookmark = existingBookmark.get();
        if (!bookmark.isStatus()) {
            // 북마크는 있지만 이미 비활성화 상태라면
            throw new BookmarkAlreadyDeactivatedException(); // "이미 북마크가 비활성화 상태입니다" 예외 발생
        }

        // 활성 상태의 북마크이므로 비활성화 처리
        bookmark.deactivate();
        bookmarkRepository.save(bookmark);

        return ResponseDTO.ok();
    }

    @Transactional(readOnly = true)
    public boolean getBookmarkStatus(PrincipalDetails principalDetails, Long recipeId) {
        if (principalDetails == null || principalDetails.getMember() == null) {
            // 로그인하지 않은 사용자라면 북마크 상태를 false로 간주
            return false;
        }
        Member member = principalDetails.getMember();

        Recipes recipe = recipesRepository.findById(recipeId)
                .orElseThrow(RecipeIdIsInvalidException::new);

        return bookmarkRepository.existsByMemberAndRecipeAndStatus(member, recipe, true);
    }

    @Transactional(readOnly = true)
    public ResponseDTO<Page<BookmarkResponseDto>> getMyBookmarks(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(UserNotFoundException::new);

        Page<Bookmark> bookmarksPage = bookmarkRepository.findByMemberAndStatusTrue(member, pageable);

        Page<BookmarkResponseDto> dtoPage = bookmarksPage.map(bookmark -> new BookmarkResponseDto(bookmark.getRecipe()));
        return ResponseDTO.okWithData(dtoPage);
    }
}