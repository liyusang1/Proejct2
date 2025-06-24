package org.example.project2.domain.reports.repository;

import org.example.project2.domain.reports.entity.Reports;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportsRepository
        extends JpaRepository<Reports, Long> {

    Optional<Reports> findByMember_IdAndItemId(Long memberId, Long itemId);
}
