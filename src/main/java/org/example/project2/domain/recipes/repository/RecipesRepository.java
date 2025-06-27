package org.example.project2.domain.recipes.repository;

import org.example.project2.domain.member.entity.Member;
import org.example.project2.domain.recipes.entity.Recipes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RecipesRepository extends JpaRepository<Recipes, Long> {
    Page<Recipes> findByMember(Member member, Pageable pageable);
}