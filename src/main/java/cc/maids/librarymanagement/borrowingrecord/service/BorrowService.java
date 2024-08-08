package cc.maids.librarymanagement.borrowingrecord.service;

import cc.maids.librarymanagement.book.exception.BookNotFoundException;
import cc.maids.librarymanagement.borrowingrecord.entity.BorrowingRecord;
import cc.maids.librarymanagement.borrowingrecord.exception.BookAlreadyBorrowedException;
import cc.maids.librarymanagement.patron.exception.PatronNotFoundException;

/**
 * Service interface for Borrowing Books .
 * <p>
 * This interface defines methods for borrowing  books.
 */
public interface BorrowService {
    /**
     * Borrows a book for a patron.
     *
     * @param bookId          The ID of the book to borrow.
     * @param patronId        The ID of the patron borrowing the book.
     * @param borrowingRecord The BorrowingRecord object containing the details of the borrowing.
     * @return The BorrowingRecord object representing the borrowing with null return date.
     * @throws BookAlreadyBorrowedException If the book is already borrowed by another patron.
     * @throws BookNotFoundException        If the book is not found.
     * @throws PatronNotFoundException      If the patron is not found.
     */
    BorrowingRecord borrowBook(Long bookId, Long patronId, BorrowingRecord borrowingRecord);

}
