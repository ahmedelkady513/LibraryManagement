package cc.maids.librarymanagement.borrowingrecord.controller;

import cc.maids.librarymanagement.borrowingrecord.entity.BorrowingRecord;
import cc.maids.librarymanagement.borrowingrecord.mapper.BorrowingRecordMapper;
import cc.maids.librarymanagement.borrowingrecord.request.BorrowBookRequest;
import cc.maids.librarymanagement.borrowingrecord.request.ReturnBookRequest;
import cc.maids.librarymanagement.borrowingrecord.response.BorrowBookResponse;
import cc.maids.librarymanagement.borrowingrecord.response.ReturnBookResponse;
import cc.maids.librarymanagement.borrowingrecord.service.BorrowService;
import cc.maids.librarymanagement.borrowingrecord.service.ReturnService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BorrowingRecordController {

    private final BorrowService borrowService;
    private final ReturnService returnService;
    private final BorrowingRecordMapper borrowingRecordMapper;

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowBookResponse> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId, @Valid @RequestBody BorrowBookRequest borrowBookRequest) {
        BorrowingRecord borrowingRecord = borrowingRecordMapper.borrowBookRequestToBorrowingRecord(borrowBookRequest);
        BorrowBookResponse borrowBookResponse = borrowingRecordMapper.borrowingRecordToBorrowBookResponse(borrowService.borrowBook(bookId, patronId, borrowingRecord));
        return ResponseEntity.ok(borrowBookResponse);
    }

    @PostMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<ReturnBookResponse> returnBook(@PathVariable Long bookId, @PathVariable Long patronId, @Valid @RequestBody ReturnBookRequest returnBookRequest) {
        BorrowingRecord returnRecord = borrowingRecordMapper.returnBookRequestToBorrowingRecord(returnBookRequest);
        ReturnBookResponse returnBookResponse = borrowingRecordMapper.borrowingRecordToReturnBookResponse(returnService.returnBook(bookId, patronId, returnRecord));
        return ResponseEntity.ok(returnBookResponse);
    }
}
