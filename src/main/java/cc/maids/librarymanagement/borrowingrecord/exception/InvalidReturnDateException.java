package cc.maids.librarymanagement.borrowingrecord.exception;

public class InvalidReturnDateException extends RuntimeException {
    public InvalidReturnDateException(String message) {
        super(message);
    }
}
