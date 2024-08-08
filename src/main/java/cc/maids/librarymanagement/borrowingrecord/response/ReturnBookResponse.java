package cc.maids.librarymanagement.borrowingrecord.response;

import cc.maids.librarymanagement.book.response.GetBookResponse;
import cc.maids.librarymanagement.patron.response.GetPatronResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnBookResponse {

    private Long id;
    private GetBookResponse book;
    private GetPatronResponse patron;
    private LocalDateTime borrowingDate;
    private LocalDateTime returnDate;
}
