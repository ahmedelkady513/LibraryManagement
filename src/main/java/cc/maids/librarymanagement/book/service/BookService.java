package cc.maids.librarymanagement.book.service;

import cc.maids.librarymanagement.book.entity.Book;
import cc.maids.librarymanagement.book.exception.BookCannotBeDeletedException;
import cc.maids.librarymanagement.book.exception.BookNotFoundException;
import cc.maids.librarymanagement.book.exception.DuplicateISBNException;

import java.util.List;

/**
 * Service interface for managing books.
 * <p>
 * This interface defines methods for creating, retrieving, updating, and deleting book records.
 */
public interface BookService {

    /**
     * Retrieves a list of all books in the system.
     *
     * @return A list of all books in the system.
     */
    List<Book> getBooks();

    /**
     * Retrieves a specific book by ID.
     *
     * @param id The ID of the book to retrieve.
     * @return The Book object with the specified ID.
     * @throws BookNotFoundException If no book with the specified ID exists in the system.
     */
    Book getBook(Long id);

    /**
     * Creates a new book in the system.
     *
     * @param book The Book object containing the details of the book to be created.
     * @return The created Book object with an assigned ID.
     * @throws DuplicateISBNException If a book with the same ISBN already exists in the system.
     */
    Book createBook(Book book);

    /**
     * Updates the details of a book in the system.
     *
     * @param id          The ID of the book to update.
     * @param updatedBook The Book object containing the new details for the book.
     * @return The updated Book object.
     * @throws BookNotFoundException  If no book with the specified ID exists in the system.
     * @throws DuplicateISBNException If a book with the same ISBN already exists in the system.
     */
    Book updateBook(Long id, Book updatedBook);

    /**
     * Deletes a book from the system.
     *
     * @param id The ID of the book to delete.
     * @throws BookCannotBeDeletedException If the book cannot be deleted (e.g., because it is currently borrowed).
     */
    void deleteBook(Long id);

}
