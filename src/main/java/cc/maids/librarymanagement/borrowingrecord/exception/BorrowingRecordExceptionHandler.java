package cc.maids.librarymanagement.borrowingrecord.exception;

import cc.maids.librarymanagement.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BorrowingRecordExceptionHandler {
    @ExceptionHandler(BookAlreadyBorrowedException.class)
    public ResponseEntity<ErrorResponse> handleBookAlreadyBorrowedExceptionHandler(BookAlreadyBorrowedException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(BorrowingRecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoBorrowingRecordFoundException(BorrowingRecordNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(InvalidReturnDateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidReturnDateException(InvalidReturnDateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }
}
