package org.example.project2.domain.freeBoards.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.project2.domain.member.entity.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FreeBoardLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "free_board_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_board_id", nullable = false)
    private FreeBoards freeBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private Boolean isLike; // true = 추천, false = 비추천

    @Builder
    public FreeBoardLike(FreeBoards freeBoard, Member member, Boolean isLike) {
        this.freeBoard = freeBoard;
        this.member = member;
        this.isLike = isLike;
    }

    public void updateIsLike(Boolean isLike) {
        this.isLike = isLike;
    }
}
