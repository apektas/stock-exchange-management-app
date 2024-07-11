package com.inghub.sems.exception.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GlobalExceptionResponse {
    private Integer status;
    private String message;
    private String details;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
