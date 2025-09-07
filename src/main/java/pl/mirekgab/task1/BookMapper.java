package pl.mirekgab.task1;

import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book map(BookDTO dto) {
        Book book = new Book();
        book.setId(dto.id());
        book.setTitle(dto.title());
        book.setAuthor(dto.author());
        book.setPublishYear(dto.publishYear());
        return book;
    }

    public BookDTO map(Book book) {
        BookDTO bookDTO = new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getPublishYear());
        return bookDTO;
    }
}
