package org.example.project2.domain.item.repository;

import org.example.project2.domain.item.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository
        extends JpaRepository<Items, Long> {
}
