package pl.mirekgab.task1;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record NewBookDTO (
        @NotBlank(message = "title is mandatory")
        String title,
        @NotEmpty(message="author is mandatory")
        String author,
        int publishYear
) {
}
