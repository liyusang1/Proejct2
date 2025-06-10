package org.example.project2.domain.reports.controller;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.reports.service.ReportService;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/{itemId}")
    public ResponseEntity<ResponseDTO<Void>> postReport(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long itemId) {

        ResponseDTO<Void> response = reportService.postReport(
                principalDetails,
                itemId);

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }
}