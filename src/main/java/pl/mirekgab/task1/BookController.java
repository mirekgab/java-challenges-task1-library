package pl.mirekgab.task1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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

    @PostMapping("/api/book/create")
    public ResponseEntity<BookDTO> createBook(@RequestBody NewBookDTO bookDTO) {
        BookDTO createdBook = bookService.createBook(bookDTO);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @PatchMapping("/api/book/update")
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO bookDTO) {
        BookDTO updatedBook = this.bookService.update(bookDTO);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }


}
