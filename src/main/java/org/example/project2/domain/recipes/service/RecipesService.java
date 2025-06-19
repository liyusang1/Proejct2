package org.example.project2.domain.recipes.service;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.domain.member.exception.UserNotFoundException;
import org.example.project2.domain.member.repository.MemberRepository;
import org.example.project2.domain.recipes.dto.request.RecipeRequestDto;
import org.example.project2.domain.recipes.dto.response.RecipeDetailResponseDto;
import org.example.project2.domain.recipes.dto.response.RecipeResponseDto;
import org.example.project2.domain.recipes.entity.Recipes;
import org.example.project2.domain.recipes.exception.RecipeCookingTimeException;
import org.example.project2.domain.recipes.exception.RecipeDescriptionLengthIsLongException;
import org.example.project2.domain.recipes.exception.RecipeIdIsInvalidException;
import org.example.project2.domain.recipes.exception.RecipeTitleLengthIsLongException;
import org.example.project2.domain.recipes.repository.RecipesRepository;
import org.example.project2.global.exception.PermissionDeniedException;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
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

    //리스트 조회
    public ResponseDTO<Page<RecipeResponseDto>> getRecipeList(Pageable pageable) {
        return
                ResponseDTO.okWithData(
                        recipesRepository.findAll(pageable)
                                .map(RecipeResponseDto::fromEntity));
    }

    //상세 조회
    public ResponseDTO<RecipeDetailResponseDto> getRecipeById(Long id) {
        Recipes recipe = recipesRepository.findById(id)
                .orElseThrow(() -> new RecipeIdIsInvalidException());

        recipe.updateViewCount();

        return ResponseDTO.okWithData(
                RecipeDetailResponseDto.fromEntity(recipe));
    }

    //레시피 등록
    public ResponseDTO<Void> createRecipe(PrincipalDetails principalDetails, RecipeRequestDto dto) {
        if (principalDetails == null) {
            throw new UserNotFoundException();
        }
        if (dto.title() != null && dto.title().length() > 20) {
            throw new RecipeTitleLengthIsLongException();
        }
        if (dto.description() != null && dto.description().length() > 25) {
            throw new RecipeDescriptionLengthIsLongException();
        }
        if (dto.time() < 1) {
            throw new RecipeCookingTimeException();
        }

        Member member = principalDetails.getMember();
        Recipes recipes = dto.toEntity(member);
        recipesRepository.save(recipes);

        return ResponseDTO.ok();
    }

    //레시피 수정
    public ResponseDTO<Void> updateRecipe(Long recipeId, RecipeRequestDto dto, PrincipalDetails principalDetails) {

        if (principalDetails == null) {
            throw new UserNotFoundException();
        }
        Member member = principalDetails.getMember();

        Recipes recipe = recipesRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeIdIsInvalidException());

        // 작성자 본인인지 확인
        if (!recipe.getMember().getId().equals(member.getId())) {
            throw new PermissionDeniedException();
        }
        if (dto.title() != null && dto.title().length() > 20) {
            throw new RecipeTitleLengthIsLongException();
        }
        if (dto.description() != null && dto.description().length() > 25) {
            throw new RecipeDescriptionLengthIsLongException();
        }
        if (dto.time() < 1) {
            throw new RecipeCookingTimeException();
        }

        recipe.update(dto.title(), dto.description(), dto.ingredients(),
                dto.imageUrl(), dto.time(), dto.level(), dto.steps());

        return ResponseDTO.ok();
    }

    //레시피 삭제
    public ResponseDTO<Void> deleteRecipe(Long recipeId, PrincipalDetails principalDetails) {

        if (principalDetails == null) {
            throw new UserNotFoundException();
        }

        Member member = principalDetails.getMember();
        Recipes recipe = recipesRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeIdIsInvalidException());

        // 작성자 본인인지 확인
        if (!recipe.getMember().getId().equals(member.getId())) {
            throw new PermissionDeniedException();
        }
        recipesRepository.delete(recipe);
        return ResponseDTO.ok();
    }
}