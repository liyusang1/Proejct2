package org.example.project2.domain.notifications.repository;

import jakarta.transaction.Transactional;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.domain.notifications.entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationsRepository
        extends JpaRepository<Notifications, Long> {

    List<Notifications> findAllByMember_IdOrderByCreatedAtDesc(Long memberId);

    @Modifying
    @Transactional
    @Query("UPDATE Notifications n SET n.isRead = TRUE WHERE n.member = :member")
    int updateAllNotificationsAsReadByMember(@Param("member") Member member);

    @Modifying
    @Transactional
    @Query("DELETE FROM Notifications n WHERE n.member = :member AND n.isRead = TRUE")
    int deleteByMemberAndIsReadTrue(@Param("member") Member member);

    boolean existsByMember_IdAndIsReadFalse(Long memberId);
}
