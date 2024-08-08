package cc.maids.librarymanagement.book.service;

import cc.maids.librarymanagement.book.entity.Book;
import cc.maids.librarymanagement.book.exception.BookCannotBeDeletedException;
import cc.maids.librarymanagement.book.exception.BookNotFoundException;
import cc.maids.librarymanagement.book.exception.DuplicateISBNException;
import cc.maids.librarymanagement.book.repository.BookRepository;
import cc.maids.librarymanagement.borrowingrecord.service.BorrowingRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BorrowingRecordService borrowingRecordService;

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
    }

    @Override
    @Transactional
    public Book createBook(Book book) {
        checkIfBookISBNExists(book.getIsbn());
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book updateBook(Long id, Book updatedBook) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));

        checkIfBookISBNExists(updatedBook.getIsbn());

        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setIsbn(updatedBook.getIsbn());

        return bookRepository.save(existingBook);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        if (borrowingRecordService.isBookBorrowed(id)) {
            throw new BookCannotBeDeletedException("Book with id: " + id + " cannot be deleted because it is currently borrowed");
        }
        bookRepository.deleteById(id);
    }

    private void checkIfBookISBNExists(String isbn) {
        if (bookRepository.existsByIsbn(isbn)) {
            throw new DuplicateISBNException("Book with ISBN: " + isbn + " already exists");
        }
    }


}
