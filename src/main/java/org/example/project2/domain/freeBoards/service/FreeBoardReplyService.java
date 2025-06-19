package org.example.project2.domain.freeBoards.service;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.freeBoards.dto.request.PostFreeBoardReplyRequestDto;
import org.example.project2.domain.freeBoards.dto.response.FreeBoardReplyResponseDto;
import org.example.project2.domain.freeBoards.entity.FreeBoardReplies;
import org.example.project2.domain.freeBoards.exception.FreeBoardReplyIdIsInvalidException;
import org.example.project2.domain.freeBoards.repository.FreeBoardReplyRepository;
import org.example.project2.domain.freeBoards.entity.FreeBoards;
import org.example.project2.domain.freeBoards.exception.FreeBoardIdIsInvalidException;
import org.example.project2.domain.freeBoards.repository.FreeBoardRepository;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.global.exception.PermissionDeniedException;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FreeBoardReplyService {

    private final FreeBoardReplyRepository freeBoardReplyRepository;
    private final FreeBoardRepository freeBoardRepository;

    public ResponseDTO<List<FreeBoardReplyResponseDto>> getAllReplies(long boardId) {
        freeBoardRepository.findById(boardId)
                .orElseThrow(FreeBoardIdIsInvalidException::new);

        List<FreeBoardReplyResponseDto> replies = freeBoardReplyRepository.findAllByFreeBoards_Id(boardId)
                .stream()
                .map(FreeBoardReplyResponseDto::fromEntity)
                .collect(Collectors.toList());

        return ResponseDTO.okWithData(replies);
    }

    public ResponseDTO<Void> postReplies(PrincipalDetails principalDetails,
                                         PostFreeBoardReplyRequestDto dto) {
        Member member = principalDetails.getMember();
        FreeBoards freeBoards = freeBoardRepository.findById(dto.freeBoardId())
                .orElseThrow(FreeBoardIdIsInvalidException::new);

        freeBoardReplyRepository.save(dto.toEntity(member, freeBoards));

        return ResponseDTO.ok();
    }

    public ResponseDTO<Void> deleteReplies(PrincipalDetails principalDetails, long replyId) {
        Member member = principalDetails.getMember();
        FreeBoardReplies reply = freeBoardReplyRepository.findById(replyId)
                .orElseThrow(FreeBoardReplyIdIsInvalidException::new);

        if (!Objects.equals(member.getId(), reply.getMember().getId())) {
            throw new PermissionDeniedException();
        }

        freeBoardReplyRepository.deleteById(replyId);

        return ResponseDTO.ok();
    }


}
