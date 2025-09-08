package pl.mirekgab.task1;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.mirekgab.task1.exception.AppRuntimeException;

import java.util.List;

@RestController
@Validated
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/book/findAll")
    public List<BookDTO> findAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/api/book/findBook/{bookId}")
    public ResponseEntity<BookDTO> findById(@PathVariable Long bookId) {
        BookDTO dto = this.bookService.findById(bookId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     *
     * @param bookDTO
     * @param bindingResult - jeżeli jest parametr, to wyrzuca wyjątek ConstraintViolationException, bez tego parametru wyrzuca MethodArgumentNotValidException
     * @return
     */
    @PostMapping("/api/book/create")
    public ResponseEntity<BookDTO> createBook(@RequestBody @Valid NewBookDTO bookDTO, BindingResult bindingResult) {
        BookDTO createdBook = bookService.createBook(bookDTO);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @PatchMapping("/api/book/update")
    public ResponseEntity<BookDTO> updateBook(@RequestBody @Valid BookDTO bookDTO) {
        BookDTO updatedBook = this.bookService.update(bookDTO);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }


}
