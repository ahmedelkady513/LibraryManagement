package cc.maids.librarymanagement.borrowingrecord.service;

/**
 * Service interface for querying borrowing records.
 * <p>
 * This interface defines methods for querying Borrowing books.
 */
public interface BorrowingRecordService {

    /**
     * Checks if a book is currently borrowed by a patron.
     *
     * @param bookId The ID of the book to check.
     * @return True if the book is currently borrowed, false otherwise.
     */
    boolean isBookBorrowed(Long bookId);

    /**
     * Checks if a patron is currently borrowing a book.
     *
     * @param patronId The ID of the patron to check.
     * @return True if the patron is currently borrowing a book, false otherwise.
     */
    boolean isPatronCurrentlyBorrowing(Long patronId);


}
