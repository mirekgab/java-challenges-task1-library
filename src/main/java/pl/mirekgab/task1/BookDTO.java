package pl.mirekgab.task1;

public record BookDTO(
        Long id,
        String title,
        String author,
        int publishYear
) {
}
