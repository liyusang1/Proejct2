package org.example.project2.domain.notifications.entity;

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
public class Notifications extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private String title;

    private String content;

    private String link;

    private Boolean isRead = false;

    @Builder
    private Notifications(Member member,String title, String content, String link) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.link = link;
    }

    public void setIsReadTrue() {
        this.isRead = true;
    }
}
