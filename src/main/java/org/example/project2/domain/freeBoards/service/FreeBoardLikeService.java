package org.example.project2.domain.freeBoards.service;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.freeBoards.entity.FreeBoardLike;
import org.example.project2.domain.freeBoards.entity.FreeBoards;
import org.example.project2.domain.freeBoards.exception.FreeBoardIdIsInvalidException;
import org.example.project2.domain.freeBoards.repository.FreeBoardLikeRepository;
import org.example.project2.domain.freeBoards.repository.FreeBoardRepository;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FreeBoardLikeService {

    private final FreeBoardRepository freeBoardRepository;
    private final FreeBoardLikeRepository freeBoardLikeRepository;

    public ResponseDTO<Void> vote(Long boardId, Member member, boolean isLike) {
        FreeBoards board = freeBoardRepository.findById(boardId)
                .orElseThrow(FreeBoardIdIsInvalidException::new);

        Optional<FreeBoardLike> existingOpt = freeBoardLikeRepository.findByFreeBoardAndMember(board, member);

        if (existingOpt.isPresent()) {
            FreeBoardLike existing = existingOpt.get();
            if (existing.getIsLike() == isLike) {
                // 같은 버튼 다시 누르면 "취소"
                freeBoardLikeRepository.delete(existing);
                if (isLike) board.decreaseLike();
                else board.decreaseDislike();
            } else {
                // 추천<->비추천 전환
                if (isLike) {
                    board.increaseLike();
                    board.decreaseDislike();
                } else {
                    board.increaseDislike();
                    board.decreaseLike();
                }
                existing.updateIsLike(isLike);
            }
        } else {
            // 첫 투표
            freeBoardLikeRepository.save(
                    FreeBoardLike.builder().freeBoard(board).member(member).isLike(isLike).build()
            );
            if (isLike) board.increaseLike();
            else board.increaseDislike();
        }
        return ResponseDTO.ok();
    }

    @Transactional(readOnly = true)
    public Optional<Boolean> myVote(Long boardId, Member member) {
        FreeBoards board = freeBoardRepository.findById(boardId)
                .orElseThrow(FreeBoardIdIsInvalidException::new);
        return freeBoardLikeRepository.findByFreeBoardAndMember(board, member)
                .map(FreeBoardLike::getIsLike);
    }
}
