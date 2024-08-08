package cc.maids.librarymanagement.book.controller;

import cc.maids.librarymanagement.book.entity.Book;
import cc.maids.librarymanagement.book.exception.BookNotFoundException;
import cc.maids.librarymanagement.book.exception.DuplicateISBNException;
import cc.maids.librarymanagement.book.mapper.BookMapper;
import cc.maids.librarymanagement.book.request.CreateBookRequest;
import cc.maids.librarymanagement.book.request.UpdateBookRequest;
import cc.maids.librarymanagement.book.response.CreateBookResponse;
import cc.maids.librarymanagement.book.response.GetBookResponse;
import cc.maids.librarymanagement.book.response.UpdateBookResponse;
import cc.maids.librarymanagement.book.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private BookMapper bookMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getBooks_shouldReturnListOfGetBookResponse() throws Exception {

        List<Book> books = Arrays.asList(
                new Book(1L, "Book Title 1", "Book Author", 2024, "978-3-16-148510-0"),
                new Book(2L, "Book Title 2", "Book Author", 2024, "978-3-16-148410-0")
        );

        List<GetBookResponse> getBookResponse = Arrays.asList(
                new GetBookResponse(1L, "Book Title 1", "Book Author", 2024, "978-3-16-148510-0"),
                new GetBookResponse(2L, "Book Title 2", "Book Author", 2024, "978-3-16-148410-0")
        );

        when(bookService.getBooks()).thenReturn(books);
        when(bookMapper.bookToGetBookResponse(books)).thenReturn(getBookResponse);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2)) // Check array length
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Book Title 1"))
                .andExpect(jsonPath("$[0].author").value("Book Author"))
                .andExpect(jsonPath("$[0].publicationYear").value(2024))
                .andExpect(jsonPath("$[0].isbn").value("978-3-16-148510-0"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("Book Title 2"))
                .andExpect(jsonPath("$[1].author").value("Book Author"))
                .andExpect(jsonPath("$[1].publicationYear").value(2024))
                .andExpect(jsonPath("$[1].isbn").value("978-3-16-148410-0"));

        verify(bookService).getBooks();
        verify(bookMapper).bookToGetBookResponse(books);
    }

    @Test
    void getBooks_whenNoBooksExist_shouldReturnEmptyList() throws Exception {
        when(bookService.getBooks()).thenReturn(Collections.emptyList());
        when(bookMapper.bookToGetBookResponse(Collections.emptyList())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));

        verify(bookService).getBooks();
        verify(bookMapper).bookToGetBookResponse(Collections.emptyList());
    }

    @Test
    void getBook_shouldReturnGetBookResponse() throws Exception {
        Long id = 1L;
        Book book = new Book(id, "Book Title", "Book Author", 2024, "978-3-16-148410-0");

        GetBookResponse getBookResponse = new GetBookResponse(id, "Book Title", "Book Author", 2024, "978-3-16-148410-0");

        when(bookService.getBook(id)).thenReturn(book);
        when(bookMapper.bookToGetBookResponse(book)).thenReturn(getBookResponse);

        mockMvc.perform(get("/books/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("Book Title"))
                .andExpect(jsonPath("$.author").value("Book Author"))
                .andExpect(jsonPath("$.publicationYear").value(2024))
                .andExpect(jsonPath("$.isbn").value("978-3-16-148410-0"));

        verify(bookService).getBook(id);
        verify(bookMapper).bookToGetBookResponse(book);
    }

    @Test
    void getBook_whenBookNotFound_shouldReturnNotFound() throws Exception {
        Long id = 1L;
        when(bookService.getBook(id)).thenThrow(new BookNotFoundException("Book not found with id: " + id));

        mockMvc.perform(get("/books/{id}", id))
                .andExpect(status().isNotFound());

        verify(bookService).getBook(id);
    }

    @Test
    void addBook_shouldCreateAndReturnCreateBookResponse() throws Exception {
        CreateBookRequest createBookRequest = new CreateBookRequest("To Kill a Mockingbird", "Harper Lee", 1960, "9780061120084");
        Book book = new Book(null, "To Kill a Mockingbird", "Harper Lee", 1960, "9780061120084");
        Book createdBook = new Book(1L, "To Kill a Mockingbird", "Harper Lee", 1960, "9780061120084");
        CreateBookResponse createBookResponse = new CreateBookResponse(1L, "To Kill a Mockingbird", "Harper Lee", 1960, "9780061120084");

        when(bookMapper.createBookRequestToBook(any(CreateBookRequest.class))).thenReturn(book);
        when(bookService.createBook(book)).thenReturn(createdBook);
        when(bookMapper.bookToCreateBookResponse(createdBook)).thenReturn(createBookResponse);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("To Kill a Mockingbird"))
                .andExpect(jsonPath("$.author").value("Harper Lee"))
                .andExpect(jsonPath("$.publicationYear").value(1960))
                .andExpect(jsonPath("$.isbn").value("9780061120084"));

        verify(bookMapper).createBookRequestToBook(any(CreateBookRequest.class));
        verify(bookService).createBook(book);
        verify(bookMapper).bookToCreateBookResponse(createdBook);
    }

    @Test
    void addBook_withInvalidData_shouldReturnBadRequest() throws Exception {
        CreateBookRequest invalidRequest = new CreateBookRequest("", "Author", -2024, "invalid-isbn");


        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addBook_withDuplicateISBN_shouldReturnConflict() throws Exception {
        CreateBookRequest request = new CreateBookRequest("To Kill a Mockingbird", "Harper Lee", 1960, "9780061120084");

        when(bookMapper.createBookRequestToBook(any(CreateBookRequest.class))).thenReturn(new Book());
        when(bookService.createBook(any(Book.class))).thenThrow(new DuplicateISBNException("Book with ISBN: " + request.getIsbn() + " already exists"));

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void updateBook_shouldUpdateAndReturnUpdateBookResponse() throws Exception {
        Long id = 1L;
        UpdateBookRequest updateBookRequest = new UpdateBookRequest("Updated Title", "Updated Author", 2024, "978-3-16-148410-0");
        Book bookUpdateData = new Book(null, "To Kill a Mockingbird", "Harper Lee", 1960, "9780061120084");
        Book updatedBook = new Book(id, "Updated Title", "Updated Author", 2024, "978-3-16-148410-0");
        UpdateBookResponse updateBookResponse = new UpdateBookResponse(id, "Updated Title", "Updated Author", 2024, "978-3-16-148410-0");

        when(bookMapper.updateBookRequestToBook(any(UpdateBookRequest.class))).thenReturn(bookUpdateData);
        when(bookService.updateBook(id, bookUpdateData)).thenReturn(updatedBook);
        when(bookMapper.bookToUpdateBookResponse(updatedBook)).thenReturn(updateBookResponse);

        mockMvc.perform(put("/books/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateBookRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.author").value("Updated Author"))
                .andExpect(jsonPath("$.publicationYear").value(2024))
                .andExpect(jsonPath("$.isbn").value("978-3-16-148410-0"));

        verify(bookMapper).updateBookRequestToBook(any(UpdateBookRequest.class));
        verify(bookService).updateBook(id, bookUpdateData);
        verify(bookMapper).bookToUpdateBookResponse(updatedBook);
    }

    @Test
    void updateBook_whenBookNotFound_shouldReturnNotFound() throws Exception {
        Long id = 1L;
        UpdateBookRequest updateBookRequest = new UpdateBookRequest("Updated Title", "Updated Author", 2024, "978-3-16-148410-0");
        Book bookUpdateData = new Book(null, "To Kill a Mockingbird", "Harper Lee", 1960, "9780061120084");

        when(bookMapper.updateBookRequestToBook(any(UpdateBookRequest.class))).thenReturn(bookUpdateData);
        when(bookService.updateBook(id, bookUpdateData)).thenThrow(new BookNotFoundException("Book not found with id: " + id));

        mockMvc.perform(put("/books/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateBookRequest)))
                .andExpect(status().isNotFound());

        verify(bookMapper).updateBookRequestToBook(any(UpdateBookRequest.class));
        verify(bookService).updateBook(id, bookUpdateData);
    }

    @Test
    void updateBook_withInvalidData_shouldReturnBadRequest() throws Exception {
        Long id = 1L;
        UpdateBookRequest invalidRequest = new UpdateBookRequest("", "Author", -2024, "invalid-isbn");

        mockMvc.perform(put("/books/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteBook_shouldReturnNoContent() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/books/{id}", id))
                .andExpect(status().isNoContent());

        verify(bookService).deleteBook(id);
    }


}



