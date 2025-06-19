package org.example.project2.domain.freeBoards.repository;

import org.example.project2.domain.freeBoards.entity.FreeBoards;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FreeBoardRepository extends JpaRepository<FreeBoards, Long> {
    // 제목에 검색어가 포함된 게시글(대소문자 구분 없이) 페이징 조회
    Page<FreeBoards> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<FreeBoards> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<FreeBoards> findAllBy(Pageable pageable);
}
