package org.example.project2.domain.freeBoards.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.project2.domain.member.entity.Member;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "free_board_view")
public class FreeBoardView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_board_id", nullable = false)
    private FreeBoards freeBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 비회원이면 null

    @Column(name = "ip_address")
    private String ipAddress; // 회원이면 null

    @Column(name = "viewed_at", nullable = false)
    private LocalDate viewedAt;

    // 생성자
    public FreeBoardView(FreeBoards freeBoard, Member member, String ipAddress, LocalDate viewedAt) {
        this.freeBoard = freeBoard;
        this.member = member;
        this.ipAddress = ipAddress;
        this.viewedAt = viewedAt;
    }
}
