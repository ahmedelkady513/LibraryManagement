package cc.maids.librarymanagement.borrowingrecord.service;

import cc.maids.librarymanagement.borrowingrecord.entity.BorrowingRecord;
import cc.maids.librarymanagement.borrowingrecord.exception.BorrowingRecordNotFoundException;

/**
 * Service interface for Returning Books .
 * <p>
 * This interface defines methods for returning  books.
 */
public interface ReturnService {

    /**
     * Returns a borrowed book.
     *
     * @param bookId       The ID of the book to return.
     * @param patronId     The ID of the patron returning the book.
     * @param returnRecord The BorrowingRecord object containing the details of the return.
     * @return The BorrowingRecord object containing the return date.
     * @throws BorrowingRecordNotFoundException If no borrowing record is found for the specified book and patron.
     */
    BorrowingRecord returnBook(Long bookId, Long patronId, BorrowingRecord returnRecord);
}
