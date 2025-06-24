package org.example.project2.domain.freeBoards.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.global.entity.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FreeBoardReplies extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "free_board_reply_id")
    private Long id;

    private String reply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_board_id", nullable = false)
    private FreeBoards freeBoards;

    @Builder
    private FreeBoardReplies(String reply, Member member, FreeBoards freeBoards) {
        this.reply = reply;
        this.member = member;
        this.freeBoards = freeBoards;
    }
}
