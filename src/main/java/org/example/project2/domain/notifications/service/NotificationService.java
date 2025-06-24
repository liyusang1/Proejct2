package org.example.project2.domain.notifications.service;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.domain.member.exception.UserNotFoundException;
import org.example.project2.domain.member.repository.MemberRepository;
import org.example.project2.domain.notifications.dto.response.GetNotificationsResponseDto;
import org.example.project2.domain.notifications.entity.Notifications;
import org.example.project2.domain.notifications.exception.InvalidNotificationIdtException;
import org.example.project2.domain.notifications.repository.NotificationsRepository;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationsRepository notificationsRepository;
    private final MemberRepository memberRepository;


    public ResponseDTO<List<GetNotificationsResponseDto>> getNotifications(PrincipalDetails principalDetails) {

        if (principalDetails == null) {
            throw new UserNotFoundException();
        }

        Member member = principalDetails.getMember();
        List<GetNotificationsResponseDto> responseDtos = new ArrayList<>();

        List<Notifications> notifications = notificationsRepository.findAllByMember_IdOrderByCreatedAtDesc(member.getId());

        for (Notifications notification : notifications) {
            responseDtos.add(GetNotificationsResponseDto.fromEntity(notification));
        }

        return ResponseDTO.okWithData(responseDtos);
    }

    public ResponseDTO<Void> readNotifications(PrincipalDetails principalDetails,
                                               Long notificationId) {

        if (principalDetails == null) {
            throw new UserNotFoundException();
        }

        Optional<Notifications> notification = notificationsRepository.findById(notificationId);
        if (notification.isEmpty()) {
            throw new InvalidNotificationIdtException();
        }

        notification.get().setIsReadTrue();

        return ResponseDTO.ok();
    }

    public ResponseDTO<Void> readAllUsersNotifications(PrincipalDetails principalDetails) {

        Optional<Member> optionalMember
                = memberRepository.findById(principalDetails.getMember().getId());

        if (optionalMember.isEmpty()) {
            throw new UserNotFoundException();
        }

        notificationsRepository.updateAllNotificationsAsReadByMember(optionalMember.get());

        return ResponseDTO.ok();
    }

    public ResponseDTO<Void> deleteAllUsersNotifications(PrincipalDetails principalDetails) {

        Optional<Member> optionalMember
                = memberRepository.findById(principalDetails.getMember().getId());

        if (optionalMember.isEmpty()) {
            throw new UserNotFoundException();
        }

        notificationsRepository.deleteByMemberAndIsReadTrue(optionalMember.get());

        return ResponseDTO.ok();
    }
}
