package cc.maids.librarymanagement.borrowingrecord.service;

import cc.maids.librarymanagement.book.entity.Book;
import cc.maids.librarymanagement.book.service.BookService;
import cc.maids.librarymanagement.borrowingrecord.entity.BorrowingRecord;
import cc.maids.librarymanagement.borrowingrecord.exception.BookAlreadyBorrowedException;
import cc.maids.librarymanagement.borrowingrecord.repository.BorrowingRecordRepository;
import cc.maids.librarymanagement.patron.entity.Patron;
import cc.maids.librarymanagement.patron.service.PatronService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowService {

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BorrowingRecordService borrowingRecordService;
    private final BookService bookService;
    private final PatronService patronService;

    @Override
    @Transactional
    public BorrowingRecord borrowBook(Long bookId, Long patronId, BorrowingRecord borrowingRecord) {
        if (borrowingRecordService.isBookBorrowed(bookId)) {
            throw new BookAlreadyBorrowedException("Book is already borrowed");
        }

        Book book = bookService.getBook(bookId);
        Patron patron = patronService.getPatron(patronId);

        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);

        return borrowingRecordRepository.save(borrowingRecord);
    }
}
