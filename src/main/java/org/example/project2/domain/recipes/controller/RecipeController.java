package org.example.project2.domain.recipes.controller;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.recipes.dto.request.RecipeRequestDto;
import org.example.project2.domain.recipes.dto.response.MyRecipeListResponseDto;
import org.example.project2.domain.recipes.dto.response.RecipeDetailResponseDto;
import org.example.project2.domain.recipes.dto.response.RecipeResponseDto;
import org.example.project2.domain.recipes.service.RecipesService;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipesService recipeService;

    //레시피 리스트 조회
    @GetMapping("")
    public ResponseEntity<ResponseDTO<Page<RecipeResponseDto>>> getRecipeList(
            @PageableDefault(size = 9, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        ResponseDTO<Page<RecipeResponseDto>> responses = recipeService.getRecipeList(pageable);
        return ResponseEntity
                .status(responses.getCode())
                .body(responses);
    }

    //레시피 id로 상세 조회
    @GetMapping("/{recipeId}")
    public ResponseEntity<ResponseDTO<RecipeDetailResponseDto>> getRecipeDetail(@PathVariable Long recipeId) {
        ResponseDTO<RecipeDetailResponseDto> responses = recipeService.getRecipeById(recipeId);
        return ResponseEntity
                .status(responses.getCode())
                .body(responses);
    }

    //레시피 등록
    @PostMapping("")
    public ResponseEntity<ResponseDTO<Void>> createRecipe(@RequestBody RecipeRequestDto dto,
                                                          @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ResponseDTO<Void> responses = recipeService.createRecipe(principalDetails, dto);
        return ResponseEntity
                .status(responses.getCode())
                .body(responses);
    }

    // 레시피 수정
    @PutMapping("/{recipeId}")
    public ResponseEntity<ResponseDTO<Void>> updateRecipe(
            @PathVariable Long recipeId,
            @RequestBody RecipeRequestDto requestDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        ResponseDTO<Void> responses = recipeService.updateRecipe(
                recipeId, requestDto, principalDetails);
        return ResponseEntity
                .status(responses.getCode())
                .body(responses);
    }

    // 레시피 삭제
    @DeleteMapping("/{recipeId}")
    public ResponseEntity<ResponseDTO<Void>> deleteRecipe(
            @PathVariable Long recipeId,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        ResponseDTO<Void> response = recipeService.deleteRecipe(
                recipeId,principalDetails);

        return ResponseEntity.status(response.getCode())
                .body(response);
    }

    //내가 쓴 레시피 조회
    @GetMapping("/postlist")
    public ResponseEntity<ResponseDTO<MyRecipeListResponseDto>> findMyRecipes(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PageableDefault(size = 6, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        ResponseDTO<MyRecipeListResponseDto> response = recipeService.findMyRecipes(principalDetails, pageable);

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }
}
