package org.example.project2.domain.recipes.dto.request;

import org.example.project2.domain.member.entity.Member;
import org.example.project2.domain.recipes.entity.Recipes;

import java.util.List;

public record RecipeRequestDto(
        String title,
        String description,
        String imageUrl,
        List<String> ingredients,
        List<String> steps
) {
    public Recipes toEntity(Member member) {
        return Recipes.builder()
                .member(member)
                .title(title)
                .description(description)
                .ingredients(ingredients)
                .imageUrl(imageUrl)
                .steps(steps)
                .build();
    }
}
