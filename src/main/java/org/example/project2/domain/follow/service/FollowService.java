package org.example.project2.domain.follow.service;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.follow.dto.response.FollowerResponseDto;
import org.example.project2.domain.follow.dto.response.FollowingResponseDto;
import org.example.project2.domain.follow.dto.response.GetIsFollowingResponseDto;
import org.example.project2.domain.follow.entity.Follow;
import org.example.project2.domain.follow.exception.SelfFollowNotAllowedException;
import org.example.project2.domain.follow.repository.FollowRepository;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.domain.member.exception.UserNotFoundException;
import org.example.project2.domain.member.repository.MemberRepository;
import org.example.project2.domain.notifications.entity.Notifications;
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
public class FollowService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;
    private final NotificationsRepository notificationsRepository;

    public ResponseDTO<Void> postFollow(PrincipalDetails principalDetails,
                                        Long MemberId) {

        if (principalDetails == null) {
            throw new UserNotFoundException();
        }

        Member followingMember = memberRepository.findById(MemberId)
                .orElseThrow(UserNotFoundException::new);

        if (principalDetails.getMember().getId().equals(followingMember.getId())) {
            throw new SelfFollowNotAllowedException();
        }

        Optional<Follow> followOptional = followRepository
                .findByFollower_IdAndFollowing_Id(
                        principalDetails.getMember().getId(), followingMember.getId());

        boolean displayNotification = false;

        if (followOptional.isPresent()) {
            Follow follow = followOptional.get();
            follow.toggleStatus();
            if (follow.getStatus()) {
                displayNotification = true;
            }
            followRepository.save(follow);
        } else {
            displayNotification = true;
            followRepository.save(Follow.builder()
                    .following(followingMember)
                    .follower(principalDetails.getMember())
                    .build()
            );
        }

        if (displayNotification) {
            Member follower = principalDetails.getMember();
            Notifications notification = Notifications.builder()
                    .title("새로운 팔로우 알림 \uD83D\uDC65")
                    .link("other-user/" + follower.getId())
                    .content(follower.getMemberBase().getNickname()
                            + "님이 회원님을 팔로우하기 시작했습니다!")
                    .member(followingMember)
                    .build();
            notificationsRepository.save(notification);
        }

        return ResponseDTO.ok();
    }

    public ResponseDTO<List<FollowerResponseDto>> getFollower(Long memberId) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);

        if (memberOptional.isEmpty()) {
            throw new UserNotFoundException();
        }

        List<Follow> follows = followRepository.findAllByFollowing_IdAndStatusTrue(memberId);
        List<FollowerResponseDto> followerResponseDtos = new ArrayList<>();

        for (Follow follow : follows) {
            followerResponseDtos.add(
                    FollowerResponseDto.fromEntity(follow));
        }

        return ResponseDTO.
                okWithData(followerResponseDtos);
    }

    public ResponseDTO<List<FollowingResponseDto>> getFollowing(Long memberId) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);

        if (memberOptional.isEmpty()) {
            throw new UserNotFoundException();
        }

        List<Follow> follows = followRepository.findAllByFollower_IdAndStatusTrue(memberId);
        List<FollowingResponseDto> followingResponseDtos = new ArrayList<>();

        for (Follow follow : follows) {
            followingResponseDtos.add(
                    FollowingResponseDto.fromEntity(follow));
        }

        return ResponseDTO.
                okWithData(followingResponseDtos);
    }

    public ResponseDTO<GetIsFollowingResponseDto> getIsFollowing(
            PrincipalDetails principalDetails, Long memberId) {

        if (principalDetails == null) {
            throw new UserNotFoundException();
        }

        Member member = principalDetails.getMember();

        Member targetMember = memberRepository.findById(memberId)
                .orElseThrow(UserNotFoundException::new);

        Optional<Follow> followOptional = followRepository.
                findByFollower_IdAndFollowing_IdAndStatusTrue(member.getId(), targetMember.getId());

        boolean isFollowing = false;
        if (followOptional.isPresent()) {
            isFollowing = true;
        }

        return ResponseDTO.okWithData(
                GetIsFollowingResponseDto.fromEntity(isFollowing));
    }
}

