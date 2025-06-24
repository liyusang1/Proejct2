package org.example.project2.domain.notifications.dto.response;


import org.example.project2.domain.notifications.entity.Notifications;
import org.example.project2.global.util.DataFormatter;

public record GetNotificationsResponseDto(
        Long notificationId,
        String title,
        String content,
        String link,
        Boolean read,
        String createdAt
) {

    public static GetNotificationsResponseDto fromEntity(Notifications notifications) {
        return new GetNotificationsResponseDto(
                notifications.getId(),
                notifications.getTitle(),
                notifications.getContent(),
                notifications.getLink(),
                notifications.getIsRead(),
                DataFormatter.getFormattedCreatedAtWithTime(notifications.getCreatedAt())
        );
    }
}
