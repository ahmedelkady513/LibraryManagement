package cc.maids.librarymanagement.borrowingRecord.controller;

import cc.maids.librarymanagement.book.entity.Book;
import cc.maids.librarymanagement.book.exception.BookNotFoundException;
import cc.maids.librarymanagement.book.response.GetBookResponse;
import cc.maids.librarymanagement.borrowingrecord.controller.BorrowingRecordController;
import cc.maids.librarymanagement.borrowingrecord.entity.BorrowingRecord;
import cc.maids.librarymanagement.borrowingrecord.exception.BookAlreadyBorrowedException;
import cc.maids.librarymanagement.borrowingrecord.exception.BorrowingRecordNotFoundException;
import cc.maids.librarymanagement.borrowingrecord.exception.InvalidReturnDateException;
import cc.maids.librarymanagement.borrowingrecord.mapper.BorrowingRecordMapper;
import cc.maids.librarymanagement.borrowingrecord.request.BorrowBookRequest;
import cc.maids.librarymanagement.borrowingrecord.request.ReturnBookRequest;
import cc.maids.librarymanagement.borrowingrecord.response.BorrowBookResponse;
import cc.maids.librarymanagement.borrowingrecord.response.ReturnBookResponse;
import cc.maids.librarymanagement.borrowingrecord.service.BorrowService;
import cc.maids.librarymanagement.borrowingrecord.service.ReturnService;
import cc.maids.librarymanagement.patron.entity.Patron;
import cc.maids.librarymanagement.patron.exception.PatronNotFoundException;
import cc.maids.librarymanagement.patron.response.GetPatronResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BorrowingRecordController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BorrowingRecordControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowService borrowService;

    @MockBean
    private ReturnService returnService;

    @MockBean
    private BorrowingRecordMapper borrowingRecordMapper;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void borrowBook_withDateAndTime_shouldReturnBorrowBookResponse() throws Exception {
        BorrowBookRequest borrowBookRequest = new BorrowBookRequest(LocalDateTime.now().minusDays(1));
        BorrowingRecord borrowingRecord = new BorrowingRecord(null, null, null, borrowBookRequest.getBorrowingDate(), null);

        Long bookId = 1L;
        Book book = new Book(bookId, "Book Title 1", "Book Author", 2024, "978-3-16-148510-0");
        GetBookResponse getBookResponse = new GetBookResponse(1L, "Book Title 1", "Book Author", 2024, "978-3-16-148510-0");

        Long patronId = 2L;
        Patron patron = new Patron(patronId, "John Doe", "john@example.com", "+1234567890");
        GetPatronResponse getPatronResponse = new GetPatronResponse(2L, "John Doe", "john@example.com", "+1234567890");

        BorrowingRecord savedBorrowingRecord = new BorrowingRecord(1L, book, patron, borrowingRecord.getBorrowingDate(), null);
        BorrowBookResponse borrowBookResponse = new BorrowBookResponse(1L, getBookResponse, getPatronResponse,
                savedBorrowingRecord.getBorrowingDate().truncatedTo(ChronoUnit.SECONDS));

        when(borrowingRecordMapper.borrowBookRequestToBorrowingRecord(any(BorrowBookRequest.class))).thenReturn(borrowingRecord);
        when(borrowService.borrowBook(book.getId(), patron.getId(), borrowingRecord)).thenReturn(savedBorrowingRecord);
        when(borrowingRecordMapper.borrowingRecordToBorrowBookResponse(savedBorrowingRecord)).thenReturn(borrowBookResponse);

        mockMvc.perform(post("/borrow/{bookId}/patron/{patronId}", bookId, patronId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(borrowBookRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(savedBorrowingRecord.getId()))
                .andExpect(jsonPath("$.borrowingDate").value(borrowBookRequest.getBorrowingDate().truncatedTo(ChronoUnit.SECONDS).toString()))
                .andExpect(jsonPath("$.book.id").value(book.getId()))
                .andExpect(jsonPath("$.patron.id").value(patron.getId()));

        verify(borrowingRecordMapper).borrowBookRequestToBorrowingRecord(any(BorrowBookRequest.class));
        verify(borrowService).borrowBook(book.getId(), patron.getId(), borrowingRecord);
        verify(borrowingRecordMapper).borrowingRecordToBorrowBookResponse(savedBorrowingRecord);
    }

    @Test
    void borrowBook_WithoutDateAndTime_shouldReturnBorrowBookResponse() throws Exception {
        BorrowBookRequest borrowBookRequest = new BorrowBookRequest();
        BorrowingRecord borrowingRecord = new BorrowingRecord(null, null, null, borrowBookRequest.getBorrowingDate(), null);

        Long bookId = 1L;
        Book book = new Book(bookId, "Book Title 1", "Book Author", 2024, "978-3-16-148510-0");
        GetBookResponse getBookResponse = new GetBookResponse(1L, "Book Title 1", "Book Author", 2024, "978-3-16-148510-0");

        Long patronId = 2L;
        Patron patron = new Patron(patronId, "John Doe", "john@example.com", "+1234567890");
        GetPatronResponse getPatronResponse = new GetPatronResponse(2L, "John Doe", "john@example.com", "+1234567890");

        BorrowingRecord savedBorrowingRecord = new BorrowingRecord(1L, book, patron, borrowingRecord.getBorrowingDate(), null);
        BorrowBookResponse borrowBookResponse = new BorrowBookResponse(1L, getBookResponse, getPatronResponse,
                savedBorrowingRecord.getBorrowingDate().truncatedTo(ChronoUnit.SECONDS));

        when(borrowingRecordMapper.borrowBookRequestToBorrowingRecord(any(BorrowBookRequest.class))).thenReturn(borrowingRecord);
        when(borrowService.borrowBook(book.getId(), patron.getId(), borrowingRecord)).thenReturn(savedBorrowingRecord);
        when(borrowingRecordMapper.borrowingRecordToBorrowBookResponse(savedBorrowingRecord)).thenReturn(borrowBookResponse);

        mockMvc.perform(post("/borrow/{bookId}/patron/{patronId}", bookId, patronId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(borrowBookRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(savedBorrowingRecord.getId()))
                .andExpect(jsonPath("$.borrowingDate").value(borrowBookRequest.getBorrowingDate().truncatedTo(ChronoUnit.SECONDS).toString()))
                .andExpect(jsonPath("$.book.id").value(book.getId()))
                .andExpect(jsonPath("$.patron.id").value(patron.getId()));

        verify(borrowingRecordMapper).borrowBookRequestToBorrowingRecord(any(BorrowBookRequest.class));
        verify(borrowService).borrowBook(book.getId(), patron.getId(), borrowingRecord);
        verify(borrowingRecordMapper).borrowingRecordToBorrowBookResponse(savedBorrowingRecord);
    }

    @Test
    void borrowBook_withFutureDateAndTime_shouldReturnBadRequest() throws Exception {
        BorrowBookRequest borrowBookRequest = new BorrowBookRequest(LocalDateTime.now().plusDays(1));

        Long bookId = 1L;
        Book book = new Book(bookId, "Book Title 1", "Book Author", 2024, "978-3-16-148510-0");

        Long patronId = 2L;
        Patron patron = new Patron(patronId, "John Doe", "john@example.com", "+1234567890");

        mockMvc.perform(post("/borrow/{bookId}/patron/{patronId}", bookId, patronId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(borrowBookRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void borrowBook_whenBookAlreadyBorrowed_shouldReturnConflict() throws Exception {
        BorrowBookRequest borrowBookRequest = new BorrowBookRequest();
        BorrowingRecord borrowingRecord = new BorrowingRecord(null, null, null, borrowBookRequest.getBorrowingDate(), null);

        Long bookId = 1L;
        Book book = new Book(bookId, "Book Title 1", "Book Author", 2024, "978-3-16-148510-0");

        Long patronId = 2L;
        Patron patron = new Patron(patronId, "John Doe", "john@example.com", "+1234567890");

        when(borrowingRecordMapper.borrowBookRequestToBorrowingRecord(any(BorrowBookRequest.class))).thenReturn(borrowingRecord);
        when(borrowService.borrowBook(book.getId(), patron.getId(), borrowingRecord)).thenThrow(new BookAlreadyBorrowedException("Book is already borrowed"));

        mockMvc.perform(post("/borrow/{bookId}/patron/{patronId}", bookId, patronId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(borrowBookRequest)))
                .andExpect(status().isConflict());

        verify(borrowingRecordMapper).borrowBookRequestToBorrowingRecord(any(BorrowBookRequest.class));
        verify(borrowService).borrowBook(book.getId(), patron.getId(), borrowingRecord);
    }

    @Test
    void borrowBook_whenNoBookFound_shouldReturnNotFound() throws Exception {
        BorrowBookRequest borrowBookRequest = new BorrowBookRequest();
        BorrowingRecord borrowingRecord = new BorrowingRecord(null, null, null, borrowBookRequest.getBorrowingDate(), null);

        Long bookId = 1L;
        Book book = new Book(bookId, "Book Title 1", "Book Author", 2024, "978-3-16-148510-0");

        Long patronId = 2L;
        Patron patron = new Patron(patronId, "John Doe", "john@example.com", "+1234567890");

        when(borrowingRecordMapper.borrowBookRequestToBorrowingRecord(any(BorrowBookRequest.class))).thenReturn(borrowingRecord);
        when(borrowService.borrowBook(book.getId(), patron.getId(), borrowingRecord)).thenThrow(new BookNotFoundException("Book not found with id: " + bookId));

        mockMvc.perform(post("/borrow/{bookId}/patron/{patronId}", bookId, patronId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(borrowBookRequest)))
                .andExpect(status().isNotFound());

        verify(borrowingRecordMapper).borrowBookRequestToBorrowingRecord(any(BorrowBookRequest.class));
        verify(borrowService).borrowBook(book.getId(), patron.getId(), borrowingRecord);

    }

    @Test
    void borrowBook_whenNoPatronFound_shouldReturnNotFound() throws Exception {
        BorrowBookRequest borrowBookRequest = new BorrowBookRequest();
        BorrowingRecord borrowingRecord = new BorrowingRecord(null, null, null, borrowBookRequest.getBorrowingDate(), null);

        Long bookId = 1L;
        Book book = new Book(bookId, "Book Title 1", "Book Author", 2024, "978-3-16-148510-0");

        Long patronId = 2L;
        Patron patron = new Patron(patronId, "John Doe", "john@example.com", "+1234567890");

        when(borrowingRecordMapper.borrowBookRequestToBorrowingRecord(any(BorrowBookRequest.class))).thenReturn(borrowingRecord);
        when(borrowService.borrowBook(book.getId(), patron.getId(), borrowingRecord)).thenThrow(new PatronNotFoundException("Patron not found with id: " + patronId));

        mockMvc.perform(post("/borrow/{bookId}/patron/{patronId}", bookId, patronId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(borrowBookRequest)))
                .andExpect(status().isNotFound());

        verify(borrowingRecordMapper).borrowBookRequestToBorrowingRecord(any(BorrowBookRequest.class));
        verify(borrowService).borrowBook(book.getId(), patron.getId(), borrowingRecord);

    }

    @Test
    void returnBook_WithDateAndTime_shouldReturnReturnBookResponse() throws Exception {
        ReturnBookRequest returnBookRequest = new ReturnBookRequest(LocalDateTime.now().minusHours(1));
        BorrowingRecord borrowingRecord = new BorrowingRecord(null, null, null, null, returnBookRequest.getReturnDate());

        Long bookId = 1L;
        Book book = new Book(bookId, "Book Title 1", "Book Author", 2024, "978-3-16-148510-0");
        GetBookResponse getBookResponse = new GetBookResponse(1L, "Book Title 1", "Book Author", 2024, "978-3-16-148510-0");

        Long patronId = 2L;
        Patron patron = new Patron(patronId, "John Doe", "john@example.com", "+1234567890");
        GetPatronResponse getPatronResponse = new GetPatronResponse(2L, "John Doe", "john@example.com", "+1234567890");

        BorrowingRecord savedBorrowingRecord = new BorrowingRecord(1L, book, patron, LocalDateTime.now().minusDays(1), borrowingRecord.getReturnDate());
        ReturnBookResponse returnBookResponse = new ReturnBookResponse(1L, getBookResponse, getPatronResponse,
                savedBorrowingRecord.getBorrowingDate().truncatedTo(ChronoUnit.SECONDS), savedBorrowingRecord.getReturnDate().truncatedTo(ChronoUnit.SECONDS));

        when(borrowingRecordMapper.returnBookRequestToBorrowingRecord(any(ReturnBookRequest.class))).thenReturn(borrowingRecord);
        when(returnService.returnBook(book.getId(), patron.getId(), borrowingRecord)).thenReturn(savedBorrowingRecord);
        when(borrowingRecordMapper.borrowingRecordToReturnBookResponse(savedBorrowingRecord)).thenReturn(returnBookResponse);

        mockMvc.perform(post("/return/{bookId}/patron/{patronId}", bookId, patronId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(returnBookRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(savedBorrowingRecord.getId()))
                .andExpect(jsonPath("$.borrowingDate").value(savedBorrowingRecord.getBorrowingDate().truncatedTo(ChronoUnit.SECONDS).toString()))
                .andExpect(jsonPath("$.returnDate").value(returnBookRequest.getReturnDate().truncatedTo(ChronoUnit.SECONDS).toString()))
                .andExpect(jsonPath("$.book.id").value(book.getId()))
                .andExpect(jsonPath("$.patron.id").value(patron.getId()));

        verify(borrowingRecordMapper).returnBookRequestToBorrowingRecord(any(ReturnBookRequest.class));
        verify(returnService).returnBook(bookId, patronId, borrowingRecord);
        verify(borrowingRecordMapper).borrowingRecordToReturnBookResponse(savedBorrowingRecord);
    }

    @Test
    void returnBook_WithoutDateAndTime_shouldReturnReturnBookResponse() throws Exception {
        ReturnBookRequest returnBookRequest = new ReturnBookRequest();
        BorrowingRecord borrowingRecord = new BorrowingRecord(null, null, null, null, returnBookRequest.getReturnDate());

        Long bookId = 1L;
        Book book = new Book(bookId, "Book Title 1", "Book Author", 2024, "978-3-16-148510-0");
        GetBookResponse getBookResponse = new GetBookResponse(1L, "Book Title 1", "Book Author", 2024, "978-3-16-148510-0");

        Long patronId = 2L;
        Patron patron = new Patron(patronId, "John Doe", "john@example.com", "+1234567890");
        GetPatronResponse getPatronResponse = new GetPatronResponse(2L, "John Doe", "john@example.com", "+1234567890");

        BorrowingRecord savedBorrowingRecord = new BorrowingRecord(1L, book, patron, LocalDateTime.now().minusDays(1), borrowingRecord.getReturnDate());
        ReturnBookResponse returnBookResponse = new ReturnBookResponse(1L, getBookResponse, getPatronResponse,
                savedBorrowingRecord.getBorrowingDate().truncatedTo(ChronoUnit.SECONDS), savedBorrowingRecord.getReturnDate().truncatedTo(ChronoUnit.SECONDS));

        when(borrowingRecordMapper.returnBookRequestToBorrowingRecord(any(ReturnBookRequest.class))).thenReturn(borrowingRecord);
        when(returnService.returnBook(book.getId(), patron.getId(), borrowingRecord)).thenReturn(savedBorrowingRecord);
        when(borrowingRecordMapper.borrowingRecordToReturnBookResponse(savedBorrowingRecord)).thenReturn(returnBookResponse);

        mockMvc.perform(post("/return/{bookId}/patron/{patronId}", bookId, patronId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(returnBookRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(savedBorrowingRecord.getId()))
                .andExpect(jsonPath("$.borrowingDate").value(savedBorrowingRecord.getBorrowingDate().truncatedTo(ChronoUnit.SECONDS).toString()))
                .andExpect(jsonPath("$.returnDate").value(returnBookRequest.getReturnDate().truncatedTo(ChronoUnit.SECONDS).toString()))
                .andExpect(jsonPath("$.book.id").value(book.getId()))
                .andExpect(jsonPath("$.patron.id").value(patron.getId()));

        verify(borrowingRecordMapper).returnBookRequestToBorrowingRecord(any(ReturnBookRequest.class));
        verify(returnService).returnBook(bookId, patronId, borrowingRecord);
        verify(borrowingRecordMapper).borrowingRecordToReturnBookResponse(savedBorrowingRecord);
    }

    @Test
    void returnBook_whenNoBorrowingRecordFound_shouldReturnNotFound() throws Exception {
        ReturnBookRequest returnBookRequest = new ReturnBookRequest();
        BorrowingRecord borrowingRecord = new BorrowingRecord(null, null, null, null, returnBookRequest.getReturnDate());

        Long bookId = 1L;
        Book book = new Book(bookId, "Book Title 1", "Book Author", 2024, "978-3-16-148510-0");

        Long patronId = 2L;
        Patron patron = new Patron(patronId, "John Doe", "john@example.com", "+1234567890");


        when(borrowingRecordMapper.returnBookRequestToBorrowingRecord(any(ReturnBookRequest.class))).thenReturn(borrowingRecord);
        when(returnService.returnBook(book.getId(), patron.getId(), borrowingRecord)).thenThrow(new BorrowingRecordNotFoundException("No borrowing record found"));


        mockMvc.perform(post("/return/{bookId}/patron/{patronId}", bookId, patronId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(returnBookRequest)))
                .andExpect(status().isNotFound());

        verify(borrowingRecordMapper).returnBookRequestToBorrowingRecord(any(ReturnBookRequest.class));
        verify(returnService).returnBook(book.getId(), patron.getId(), borrowingRecord);
    }

    @Test
    void returnBook_withInvalidReturnDateAndTime_shouldReturnBadRequest() throws Exception {
        ReturnBookRequest returnBookRequest = new ReturnBookRequest(LocalDateTime.now().minusDays(4));
        BorrowingRecord borrowingRecord = new BorrowingRecord(null, null, null, null, returnBookRequest.getReturnDate());

        Long bookId = 1L;
        Book book = new Book(bookId, "Book Title 1", "Book Author", 2024, "978-3-16-148510-0");
        GetBookResponse getBookResponse = new GetBookResponse(1L, "Book Title 1", "Book Author", 2024, "978-3-16-148510-0");

        Long patronId = 2L;
        Patron patron = new Patron(patronId, "John Doe", "john@example.com", "+1234567890");
        GetPatronResponse getPatronResponse = new GetPatronResponse(2L, "John Doe", "john@example.com", "+1234567890");

        BorrowingRecord savedBorrowingRecord = new BorrowingRecord(1L, book, patron, LocalDateTime.now().minusDays(1), borrowingRecord.getReturnDate());
        ReturnBookResponse returnBookResponse = new ReturnBookResponse(1L, getBookResponse, getPatronResponse,
                savedBorrowingRecord.getBorrowingDate().truncatedTo(ChronoUnit.SECONDS), savedBorrowingRecord.getReturnDate().truncatedTo(ChronoUnit.SECONDS));


        when(borrowingRecordMapper.returnBookRequestToBorrowingRecord(any(ReturnBookRequest.class))).thenReturn(borrowingRecord);
        when(returnService.returnBook(book.getId(), patron.getId(), borrowingRecord)).thenThrow(new InvalidReturnDateException("Return date cannot be before borrowing date"));

        mockMvc.perform(post("/return/{bookId}/patron/{patronId}", bookId, patronId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(returnBookRequest)))
                .andExpect(status().isBadRequest());

        verify(borrowingRecordMapper).returnBookRequestToBorrowingRecord(any(ReturnBookRequest.class));
        verify(returnService).returnBook(book.getId(), patron.getId(), borrowingRecord);
    }

    @Test
    void returnBook_withFutureReturnDateAndTime_shouldReturnBadRequest() throws Exception {
        ReturnBookRequest returnBookRequest = new ReturnBookRequest(LocalDateTime.now().plusDays(4));
        Long bookId = 1L;
        Book book = new Book(bookId, "Book Title 1", "Book Author", 2024, "978-3-16-148510-0");

        Long patronId = 2L;
        Patron patron = new Patron(patronId, "John Doe", "john@example.com", "+1234567890");


        mockMvc.perform(post("/return/{bookId}/patron/{patronId}", bookId, patronId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(returnBookRequest)))
                .andExpect(status().isBadRequest());
    }
}

