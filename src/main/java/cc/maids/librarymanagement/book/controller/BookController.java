package cc.maids.librarymanagement.book.controller;

import cc.maids.librarymanagement.book.entity.Book;
import cc.maids.librarymanagement.book.mapper.BookMapper;
import cc.maids.librarymanagement.book.request.CreateBookRequest;
import cc.maids.librarymanagement.book.request.UpdateBookRequest;
import cc.maids.librarymanagement.book.response.CreateBookResponse;
import cc.maids.librarymanagement.book.response.GetBookResponse;
import cc.maids.librarymanagement.book.response.UpdateBookResponse;
import cc.maids.librarymanagement.book.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping
    public ResponseEntity<List<GetBookResponse>> getBooks() {
        List<GetBookResponse> getBookResponse = bookMapper.bookToGetBookResponse(bookService.getBooks());
        return ResponseEntity.status(HttpStatus.OK).body(getBookResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetBookResponse> getBook(@PathVariable Long id) {
        GetBookResponse getBookResponse = bookMapper.bookToGetBookResponse(bookService.getBook(id));
        return ResponseEntity.status(HttpStatus.OK).body(getBookResponse);
    }

    @PostMapping
    public ResponseEntity<CreateBookResponse> addBook(@Valid @RequestBody CreateBookRequest createBookRequest) {
        Book book = bookService.createBook(bookMapper.createBookRequestToBook(createBookRequest));
        CreateBookResponse createBookResponse = bookMapper.bookToCreateBookResponse(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createBookResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateBookResponse> updateBook(@PathVariable Long id, @Valid @RequestBody UpdateBookRequest updateBookRequest) {
        Book book = bookService.updateBook(id, bookMapper.updateBookRequestToBook(updateBookRequest));
        UpdateBookResponse updateBookResponse = bookMapper.bookToUpdateBookResponse(book);
        return ResponseEntity.status(HttpStatus.OK).body(updateBookResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
