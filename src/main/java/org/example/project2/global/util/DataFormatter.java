package org.example.project2.global.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataFormatter {
    public static String getFormattedCreatedAt(LocalDateTime createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return createdAt.format(formatter);
    }
}
