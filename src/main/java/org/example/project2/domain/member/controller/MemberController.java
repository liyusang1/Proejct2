package org.example.project2.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project2.domain.member.dto.request.PasswordRequestDto;
import org.example.project2.domain.member.dto.request.PutMemberInfoRequestDto;
import org.example.project2.domain.member.dto.request.SignUpRequestDto;
import org.example.project2.domain.member.dto.response.MemberInfoResponseDto;
import org.example.project2.domain.member.dto.response.SignUpResponseDto;
import org.example.project2.domain.member.dto.response.TestUserResponseDto;
import org.example.project2.domain.member.entity.Member;
import org.example.project2.domain.member.service.MemberService;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO<SignUpResponseDto>> signup(
            @Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        SignUpResponseDto signUpResponseDto = memberService.signup(signUpRequestDto);
        ResponseDTO<SignUpResponseDto> response = ResponseDTO.okWithData(signUpResponseDto);
        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

    // Authenticated user 샘플테스트 코드입니다
    @GetMapping("/test/jwt")
    public ResponseEntity<ResponseDTO<TestUserResponseDto>> test(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();

        TestUserResponseDto testUserResponseDto = TestUserResponseDto.fromEntity(member);
        ResponseDTO<TestUserResponseDto> response = ResponseDTO.okWithData(testUserResponseDto);
        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

    @GetMapping("/check-duplicated-email")
    public ResponseEntity<ResponseDTO<Void>> checkDuplicateEmail(
            @RequestParam String email) {
        memberService.checkDuplicateEmail(email);
        ResponseDTO<Void> response = ResponseDTO.ok();
        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<ResponseDTO<Void>> changePassword(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @Valid @RequestBody PasswordRequestDto passwordRequestDto) {

        memberService.changePassword(principalDetails, passwordRequestDto);
        ResponseDTO<Void> response = ResponseDTO.ok();
        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

    @PostMapping("/check-password")
    public ResponseEntity<ResponseDTO<Void>> checkPassword(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @Valid @RequestBody PasswordRequestDto passwordRequestDto) {

        memberService.checkPassword(principalDetails, passwordRequestDto);
        ResponseDTO<Void> response = ResponseDTO.ok();
        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

    @GetMapping("/info")
    public ResponseEntity<ResponseDTO<MemberInfoResponseDto>> getMemberInfo(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        ResponseDTO<MemberInfoResponseDto> response = ResponseDTO.okWithData(
                memberService.getMemberInfo(principalDetails));

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

    @PutMapping("/info")
    public ResponseEntity<ResponseDTO<Void>> putMemberInfo(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @Valid @RequestBody PutMemberInfoRequestDto putMemberInfoRequestDto) {

        ResponseDTO<Void> response =
                memberService.putMemberInfo(principalDetails, putMemberInfoRequestDto);

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<ResponseDTO<MemberInfoResponseDto>> getMemberInfoByMemberId(
            @PathVariable Long memberId) {

        ResponseDTO<MemberInfoResponseDto> response =
                memberService.getMemberInfoByMemberId(memberId);

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }
}