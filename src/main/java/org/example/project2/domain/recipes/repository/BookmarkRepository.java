package org.example.project2.domain.recipes.repository;

import org.example.project2.domain.member.entity.Member;
import org.example.project2.domain.recipes.entity.Bookmark;
import org.example.project2.domain.recipes.entity.Recipes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository
        extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByMemberAndRecipe(Member member, Recipes recipe);
    Optional<Bookmark> findByMemberAndRecipeAndStatus(Member member, Recipes recipe, boolean status);
    boolean existsByMemberAndRecipeAndStatus(Member member, Recipes recipe, boolean status);
    Page<Bookmark> findByMemberAndStatusTrue(Member member, Pageable pageable);
}