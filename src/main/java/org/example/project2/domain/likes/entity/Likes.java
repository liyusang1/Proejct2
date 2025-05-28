package org.example.project2.domain.likes.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.project2.domain.item.entity.Items;
import org.example.project2.domain.member.entity.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Likes{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Items items;

    @Builder
    private Likes(Boolean status, Member member, Items items) {
        this.status = status;
        this.member = member;
        this.items = items;
    }

    public void toggleStatus() {
        if (this.status == null) {
            this.status = true;
        } else {
            this.status = !this.status;
        }
    }
}
