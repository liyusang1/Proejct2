package org.example.project2.domain.freeBoards.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.project2.domain.freeBoards.dto.request.PostFreeBoardRequestDto;
import org.example.project2.domain.freeBoards.dto.response.FreeBoardResponseDto;
import org.example.project2.domain.freeBoards.entity.FreeBoardView;
import org.example.project2.domain.freeBoards.entity.FreeBoards;
import org.example.project2.domain.freeBoards.exception.FreeBoardIdIsInvalidException;
import org.example.project2.domain.freeBoards.repository.FreeBoardReplyRepository;
import org.example.project2.domain.freeBoards.repository.FreeBoardRepository;
import org.example.project2.domain.freeBoards.repository.FreeBoardViewRepository;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.domain.member.repository.MemberRepository;
import org.example.project2.global.exception.PermissionDeniedException;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class FreeBoardService {
    private final FreeBoardReplyRepository freeBoardReplyRepository;

    private final FreeBoardViewRepository freeBoardViewRepository;
    // 자유게시판 레포지토리
    private final FreeBoardRepository freeBoardRepository;
    // 멤버(회원) 레포지토리
    private final MemberRepository memberRepository;

    /**
     * 자유게시판 전체 목록 조회(페이징, 검색 포함)
     *
     * @param pageable 페이징 정보
     * @param search   검색어(제목)
     * @return 게시글 DTO 페이지
     */
    public ResponseDTO<Page<FreeBoardResponseDto>> getAllBoards(Pageable pageable, String search, String category) {
        Page<FreeBoards> boardsPage;
        boolean hasCategory = category != null && !category.isEmpty();
        boolean hasSearch = search != null && !search.trim().isEmpty();

        if (category != null && !category.isEmpty()) {
            // 검색 + 카테고리
            if (search != null && !search.trim().isEmpty()) {
                boardsPage = freeBoardRepository.findAllByCategoryAndTitleContainingIgnoreCase(category, search, pageable);
            } else {
                boardsPage = freeBoardRepository.findAllByCategory(category, pageable);
            }
        } else {
            // 전체
            if (search != null && !search.trim().isEmpty()) {
                boardsPage = freeBoardRepository.findAllByTitleContainingIgnoreCase(search, pageable);
            } else {
                boardsPage = freeBoardRepository.findAllBy(pageable);
            }
        }

        // Entity → DTO 매핑
        Page<FreeBoardResponseDto> dtoPage = boardsPage.map(board -> {
            int replyCount = freeBoardReplyRepository.findAllByFreeBoards_Id(board.getId()).size();
            return FreeBoardResponseDto.fromEntity(board, replyCount);
        });
        // 표준 응답 포맷으로 반환
        return ResponseDTO.okWithData(dtoPage);
    }

    /**
     * 게시글 상세조회(조회수 증가)
     *
     * @param boardId 게시글 PK
     * @return 게시글 상세 DTO
     */
    public ResponseDTO<FreeBoardResponseDto> getBoardDetail(long boardId, HttpServletRequest request, Member member) {
        // 게시글 조회 (없으면 예외 발생)
        FreeBoards board = freeBoardRepository.findById(boardId)
                .orElseThrow(FreeBoardIdIsInvalidException::new);
        LocalDate today = LocalDate.now();
        boolean alreadyViewed;
        if (member != null) {
            alreadyViewed = freeBoardViewRepository.existsByFreeBoardAndMemberAndViewedAt(board, member, today);
        } else {
            String ipAddress = request.getRemoteAddr();
            alreadyViewed = freeBoardViewRepository.existsByFreeBoardAndIpAddressAndViewedAt(board, ipAddress, today);
        }

        if (!alreadyViewed) {
            board.updateViewCount();
            FreeBoardView view = new FreeBoardView(
                    board,
                    member,
                    member == null ? request.getRemoteAddr() : null,
                    today
            );
            freeBoardViewRepository.save(view);
        }
        int replyCount = freeBoardReplyRepository.findAllByFreeBoards_Id(board.getId()).size();

        // Entity → DTO 변환 후 반환
        return ResponseDTO.okWithData(FreeBoardResponseDto.fromEntity(board, replyCount));
    }

    /**
     * 게시글 작성
     *
     * @param principalDetails 로그인한 유저 정보
     * @param requestDto       게시글 작성 DTO
     * @return 성공 응답
     */
    public ResponseDTO<Void> postBoard(PrincipalDetails principalDetails,
                                       PostFreeBoardRequestDto requestDto) {
        // 로그인 멤버 정보
        Member member = principalDetails.getMember();

        // 실제 DB에 존재하는 회원인지 확인(권한 문제 대비)
        Member managedMember = memberRepository.findById(member.getId())
                .orElseThrow(PermissionDeniedException::new);

        // DTO → Entity 변환 후 저장
        freeBoardRepository.save(requestDto.toEntity(member));
        // 성공 응답 반환
        return ResponseDTO.ok();
    }

    /**
     * 게시글 삭제(본인만 가능)
     *
     * @param principalDetails 로그인 정보
     * @param boardId          삭제할 게시글 PK
     * @return 성공 응답
     */
    public ResponseDTO<Void> deleteBoard(PrincipalDetails principalDetails, long boardId) {
        // 로그인 유저 정보
        Member member = principalDetails.getMember();

        // 삭제할 게시글 조회(없으면 예외)
        FreeBoards board = freeBoardRepository.findById(boardId)
                .orElseThrow(FreeBoardIdIsInvalidException::new);

        // 작성자가 본인인지 검증
        if (!board.getMember().getId().equals(member.getId())) {
            throw new PermissionDeniedException();
        }

        freeBoardViewRepository.deleteAllByFreeBoard(board);
        // 삭제
        freeBoardRepository.deleteById(boardId);

        return ResponseDTO.ok();
    }

    /**
     * 게시글 수정(본인만 가능)
     *
     * @param principalDetails 로그인 정보
     * @param boardId          수정할 게시글 PK
     * @param requestDto       수정 내용 DTO
     * @return 성공 응답
     */
    public ResponseDTO<Void> updateBoard(PrincipalDetails principalDetails,
                                         long boardId,
                                         PostFreeBoardRequestDto requestDto) {
        // 로그인 유저 정보
        Member member = principalDetails.getMember();

        // 수정할 게시글 조회(없으면 예외)
        FreeBoards board = freeBoardRepository.findById(boardId)
                .orElseThrow(FreeBoardIdIsInvalidException::new);

        // 작성자가 본인인지 검증
        if (!board.getMember().getId().equals(member.getId())) {
            throw new PermissionDeniedException();
        }

        // 게시글 정보 수정(제목, 내용, 카테고리, 공지, 이모지, 이미지첨부 등)
        board.updateBoard(
                requestDto.title(),
                requestDto.content(),
                requestDto.category(),
                requestDto.emoji()
        );

        return ResponseDTO.ok();
    }
}

