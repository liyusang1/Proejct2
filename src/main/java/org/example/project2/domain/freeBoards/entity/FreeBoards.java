package org.example.project2.domain.freeBoards.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.global.entity.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FreeBoards extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "free_board_id")
    private Long id;
    private String category;
    private String title;
    private String content;
    private String emoji;
    private int viewCount;
    private int likeCount = 0;
    private int dislikeCount = 0;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)  // 외래 키 이름 일치
    private Member member;

    @OneToMany(mappedBy = "freeBoards", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FreeBoardReplies> Replies = new ArrayList<>();

    @OneToMany(mappedBy = "freeBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FreeBoardLike> FreeBoardLikes = new ArrayList<>();

    @Builder
    private FreeBoards(String title, String content, String category, String emoji, Member member) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.category = category;
        this.emoji = emoji;
        this.viewCount = 0;
    }

    public void updateBoard(String title, String content, String category, String emoji) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.emoji = emoji;
    }

    public void updateViewCount() {
        this.viewCount++;
    }

    public void increaseLike() {
        this.likeCount++;
    }

    public void decreaseLike() {
        if (this.likeCount > 0) this.likeCount--;
    }

    public void increaseDislike() {
        this.dislikeCount++;
    }

    public void decreaseDislike() {
        if (this.dislikeCount > 0) this.dislikeCount--;
    }
}
