package cc.maids.librarymanagement.patron.exception;

public class PatronCannotBeDeletedException extends RuntimeException {
    public PatronCannotBeDeletedException(String message) {
        super(message);
    }
}
