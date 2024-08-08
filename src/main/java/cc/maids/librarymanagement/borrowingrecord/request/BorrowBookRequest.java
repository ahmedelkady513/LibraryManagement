package cc.maids.librarymanagement.borrowingrecord.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowBookRequest {
    @NotNull(message = "Borrowing date cannot be null")
    @PastOrPresent(message = "Borrowing date cannot be in the future")
    private LocalDateTime borrowingDate = LocalDateTime.now();
}
