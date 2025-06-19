package org.example.project2.domain.follow.entity;

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
public class Follow extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    // 누가 팔로우했는지 (팔로우하는 사람)
    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩
    @JoinColumn(name = "follower_id", nullable = false)
    private Member follower; // Member 엔티티와 연결

    // 누구를 팔로우하는지 (팔로우 당하는 사람)
    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩
    @JoinColumn(name = "following_id", nullable = false)
    private Member following; // Member 엔티티와 연결

    private Boolean status = true;

    @Builder
    private Follow(Member follower, Member following) {
        this.follower = follower;
        this.following = following;
    }

    public void toggleStatus() {
        if (this.status == null) {
            this.status = true;
        } else {
            this.status = !this.status;
        }
    }
}
