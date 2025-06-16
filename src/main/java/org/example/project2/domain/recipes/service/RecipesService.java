package org.example.project2.domain.recipes.service;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.domain.member.exception.UserNotFoundException;
import org.example.project2.domain.member.repository.MemberRepository;
import org.example.project2.domain.recipes.dto.request.RecipeRequestDto;
import org.example.project2.domain.recipes.dto.response.RecipeDetailResponseDto;
import org.example.project2.domain.recipes.dto.response.RecipeResponseDto;
import org.example.project2.domain.recipes.entity.Recipes;
import org.example.project2.domain.recipes.exception.RecipeIdIsInvalidException;
import org.example.project2.domain.recipes.repository.RecipesRepository;
import org.example.project2.global.exception.PermissionDeniedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class RecipesService {
    private final RecipesRepository recipesRepository;
    private final MemberRepository memberRepository;

    //전체 조회
    public Page<RecipeResponseDto> getRecipeList(Pageable pageable) {
        return recipesRepository.findAll(pageable)
                .map(RecipeResponseDto::fromEntity);
    }

    //상세 조회
    public RecipeDetailResponseDto getRecipeById(Long id) {
        Recipes recipe = recipesRepository.findById(id)
                .orElseThrow(() -> new RecipeIdIsInvalidException());
        return RecipeDetailResponseDto.fromEntity(recipe);
    }

    //레시피 등록
    public Long createRecipe(Long memberId, RecipeRequestDto dto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UserNotFoundException());

        Recipes recipes = dto.toEntity(member);
        recipesRepository.save(recipes);

        return recipes.getId();
    }

    //레시피 수정
    public void updateRecipe(Long recipeId, RecipeRequestDto dto, Member member) {
        Recipes recipe = recipesRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeIdIsInvalidException());

        // 작성자 본인인지 확인
        if (!recipe.getMember().getId().equals(member.getId())) {
            throw new PermissionDeniedException();
        }
        else
        recipe.update(dto.title(), dto.description(), dto.ingredients(), dto.imageUrl(), dto.steps());
    }

    //레시피 삭제
    public void deleteRecipe(Long recipeId, Member member) {
        Recipes recipe = recipesRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeIdIsInvalidException());

        // 작성자 본인인지 확인
        if (!recipe.getMember().getId().equals(member.getId())) {
            throw new PermissionDeniedException();
        }
        recipesRepository.delete(recipe);
    }
}