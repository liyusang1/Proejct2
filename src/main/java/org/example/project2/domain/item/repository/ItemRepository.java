package org.example.project2.domain.item.repository;

import org.example.project2.domain.item.entity.Items;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository
        extends JpaRepository<Items, Long> {

    Page<Items> findAll(Pageable pageable);
    Page<Items> findByNameContainingIgnoreCase(String Name, Pageable pageable);
    List<Items> findAllByMember_Id(Long memberId);
}
