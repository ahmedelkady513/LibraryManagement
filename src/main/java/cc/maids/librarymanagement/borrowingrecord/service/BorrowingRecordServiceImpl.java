package cc.maids.librarymanagement.borrowingrecord.service;

import cc.maids.librarymanagement.borrowingrecord.repository.BorrowingRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BorrowingRecordServiceImpl implements BorrowingRecordService {

    private final BorrowingRecordRepository borrowingRecordRepository;

    @Override
    public boolean isBookBorrowed(Long bookId) {
        return borrowingRecordRepository.existsByBookIdAndReturnDateIsNull(bookId);
    }

    @Override
    public boolean isPatronCurrentlyBorrowing(Long patronId) {
        return borrowingRecordRepository.existsByPatronIdAndReturnDateIsNull(patronId);
    }


}
