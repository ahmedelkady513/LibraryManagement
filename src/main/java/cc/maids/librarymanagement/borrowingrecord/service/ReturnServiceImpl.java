package cc.maids.librarymanagement.borrowingrecord.service;

import cc.maids.librarymanagement.borrowingrecord.entity.BorrowingRecord;
import cc.maids.librarymanagement.borrowingrecord.exception.BorrowingRecordNotFoundException;
import cc.maids.librarymanagement.borrowingrecord.exception.InvalidReturnDateException;
import cc.maids.librarymanagement.borrowingrecord.repository.BorrowingRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReturnServiceImpl implements ReturnService {

    private final BorrowingRecordRepository borrowingRecordRepository;

    @Override
    @Transactional
    public BorrowingRecord returnBook(Long bookId, Long patronId, BorrowingRecord returnRecord) {
        BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId).
                orElseThrow(() -> new BorrowingRecordNotFoundException("No Borrowing Record Found with BookId : " + bookId + "  and patronId : " + patronId));

        if (borrowingRecord.getBorrowingDate().isAfter(returnRecord.getReturnDate())) {
            throw new InvalidReturnDateException("Return date cannot be before borrowing date");
        }

        borrowingRecord.setReturnDate(returnRecord.getReturnDate());
        return borrowingRecordRepository.save(borrowingRecord);
    }
}
