package cc.maids.librarymanagement.book.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookResponse {
    private Long id;
    private String title;
    private String author;
    private int publicationYear;
    private String isbn;
}
