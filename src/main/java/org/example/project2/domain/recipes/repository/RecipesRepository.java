package org.example.project2.domain.recipes.repository;

import org.example.project2.domain.member.entity.Member;
import org.example.project2.domain.recipes.entity.Recipes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipesRepository extends JpaRepository<Recipes, Long> {
    List<Recipes> findByMember(Member member);
    List<Recipes> findByMemberId(Long memberId);
}
