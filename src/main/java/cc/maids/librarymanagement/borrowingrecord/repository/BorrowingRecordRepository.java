package cc.maids.librarymanagement.borrowingrecord.repository;

import cc.maids.librarymanagement.borrowingrecord.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {

    boolean existsByBookIdAndReturnDateIsNull(Long bookId);

    boolean existsByPatronIdAndReturnDateIsNull(Long patronId);

    Optional<BorrowingRecord> findByBookIdAndPatronIdAndReturnDateIsNull(Long bookId, Long patronId);
}
