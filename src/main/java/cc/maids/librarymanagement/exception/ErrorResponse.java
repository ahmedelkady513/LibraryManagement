package cc.maids.librarymanagement.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Slf4j
public class ErrorResponse {
    private final String message;
    private final LocalDateTime timestamp;
    private final UUID errorUUID;

    public ErrorResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.errorUUID = UUID.randomUUID();
        log.error("Error occurred: {} - Timestamp: {} - UUID: {}", message, timestamp, errorUUID);
    }
}
