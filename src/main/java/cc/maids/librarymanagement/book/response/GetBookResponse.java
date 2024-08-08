package cc.maids.librarymanagement.book.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetBookResponse {
    private Long id;
    private String title;
    private String author;
    private int publicationYear;
    private String isbn;
}
