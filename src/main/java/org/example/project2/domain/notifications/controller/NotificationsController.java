package org.example.project2.domain.notifications.controller;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.notifications.dto.response.GetNotificationsResponseDto;
import org.example.project2.domain.notifications.service.NotificationService;
import org.example.project2.global.springsecurity.PrincipalDetails;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationsController {

    private final NotificationService notificationService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO<List<GetNotificationsResponseDto>>> getNotifications(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        ResponseDTO<List<GetNotificationsResponseDto>> response
                = notificationService.getNotifications(principalDetails);

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity<ResponseDTO<Void>> readNotifications(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long notificationId) {

        ResponseDTO<Void> response
                = notificationService.readNotifications(principalDetails,notificationId);

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

    @PutMapping("/read-all")
    public ResponseEntity<ResponseDTO<Void>> readAllUsersNotifications(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        ResponseDTO<Void> response
                = notificationService.readAllUsersNotifications(principalDetails);

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<ResponseDTO<Void>> deleteAllUsersNotifications(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        ResponseDTO<Void> response
                = notificationService.deleteAllUsersNotifications(principalDetails);

        return ResponseEntity
                .status(response.getCode())
                .body(response);
    }
}