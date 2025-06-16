package org.example.project2.domain.recipes.controller;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.recipes.dto.request.RecipeRequestDto;
import org.example.project2.domain.recipes.dto.response.RecipeDetailResponseDto;
import org.example.project2.domain.recipes.dto.response.RecipeResponseDto;
import org.example.project2.domain.recipes.service.RecipesService;
import org.example.project2.global.springsecurity.PrincipalDetails;
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
    public ResponseEntity<Page<RecipeResponseDto>> getRecipeList(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<RecipeResponseDto> recipes = recipeService.getRecipeList(pageable);
        return ResponseEntity.ok(recipes);
    }

    //레시피 id로 상세 조회
    @GetMapping("/{recipeId}")
    public ResponseEntity<RecipeDetailResponseDto> getRecipeDetail(@PathVariable Long recipeId) {
        RecipeDetailResponseDto recipeDetail = recipeService.getRecipeById(recipeId);
        return ResponseEntity.ok(recipeDetail);
    }

    //레시피 등록
    @PostMapping("")
    public ResponseEntity<?> createRecipe(@RequestBody RecipeRequestDto dto,
                                          @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long recipeId = recipeService.createRecipe(principalDetails.getMember().getId(), dto);
        return ResponseEntity.ok("레시피 등록 완료 (ID: " + recipeId + ")");
    }

    // 레시피 수정
    @PutMapping("/{recipeId}")
    public ResponseEntity<String> updateRecipe(
            @PathVariable Long recipeId,
            @RequestBody RecipeRequestDto requestDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        recipeService.updateRecipe(recipeId, requestDto, principalDetails.getMember());
        return ResponseEntity.ok("레시피가 수정되었습니다");
    }

    // 레시피 삭제
    @DeleteMapping("/{recipeId}")
    public ResponseEntity<String> deleteRecipe(
            @PathVariable Long recipeId,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        recipeService.deleteRecipe(recipeId, principalDetails.getMember());
        return ResponseEntity.ok("레시피가 삭제되었습니다.");
    }


}
