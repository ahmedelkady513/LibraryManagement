package cc.maids.librarymanagement.borrowingrecord.exception;

public class BookAlreadyBorrowedException extends RuntimeException{
    public BookAlreadyBorrowedException(String message) {
        super(message);
    }
}
