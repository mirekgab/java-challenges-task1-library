package pl.mirekgab.task1;

import jakarta.validation.constraints.NotBlank;

public record BookDTO(
        Long id,
        @NotBlank(message="title is mandatory")
        String title,
        @NotBlank(message="author is mandatory")
        String author,
        int publishYear
) {
}
