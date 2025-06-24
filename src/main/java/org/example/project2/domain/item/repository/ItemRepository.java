package org.example.project2.domain.item.repository;

import org.example.project2.domain.item.entity.Items;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository
        extends JpaRepository<Items, Long> {

    Page<Items> findAll(Pageable pageable);
    Page<Items> findByNameContainingIgnoreCase(String Name, Pageable pageable);
    List<Items> findAllByMember_Id(Long memberId);

    @Query("""
                SELECT i FROM Items i
                LEFT JOIN i.likes l
                WHERE l.status = true
                GROUP BY i
                ORDER BY COUNT(l) DESC
                    limit 10
            """)
    List<Items> findTop10ByLikesCount();

    @Query("SELECT COUNT(l) FROM Likes l WHERE l.items.id = :itemId AND l.status = true")
    Long countByItemIdAndStatusTrue(@Param("itemId") Long itemId);

    int countAllBy();

    int countAllByMember_Id(Long memberId);

    @Query("""
        SELECT l.items FROM Likes l
        WHERE l.member.id = :memberId AND l.status = true
        """)
    List<Items> findLikedItemsByMemberIdAndStatusTrue(@Param("memberId") Long memberId);
}
