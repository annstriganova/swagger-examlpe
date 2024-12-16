package com.example.pine.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record RequestDto(
        @Schema(title = "Name is required")
        @NotBlank String name,
        String optionalComment) {
}
