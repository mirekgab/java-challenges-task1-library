package pl.mirekgab.task1;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.mirekgab.task1.exception.AppRuntimeException;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookDTO> findAll(Pageable pageable) {
        return this.bookRepository.findAll(pageable).stream().map(book -> this.bookMapper.map(book)).toList();
    }

    public BookDTO createBook(NewBookDTO dto) {

        Book book = new Book();
        book.setTitle(dto.title());
        book.setAuthor(dto.author());
        book.setPublishYear(dto.publishYear());

        BookDTO createdBook = this.bookMapper.map(this.bookRepository.save(book));
        return createdBook;
    }

    public BookDTO update(BookDTO bookDTO) {
        Book book = this.bookRepository.findById(bookDTO.id()).orElseThrow(
                ()->new AppRuntimeException(HttpStatus.NOT_FOUND.value(), String.format("book with id %d not found", bookDTO.id())));
        book.setTitle(bookDTO.title());
        book.setAuthor(bookDTO.author());
        book.setPublishYear(bookDTO.publishYear());

        return this.bookMapper.map(this.bookRepository.save(book));
    }

    public BookDTO findById(Long bookId) {
        Book book = this.bookRepository.findById(bookId).orElseThrow(()->new AppRuntimeException(HttpStatus.NOT_FOUND.value(), "book not found"));
        return this.bookMapper.map(book);
    }
}
