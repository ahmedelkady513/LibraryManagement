package cc.maids.librarymanagement.book.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookRequest {
    
    @NotNull(message = "Title is mandatory")
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
    private String title;

    @NotNull(message = "Author is mandatory")
    @NotBlank(message = "Author cannot be blank")
    @Size(min = 1, max = 100, message = "Author must be between 1 and 100 characters")
    private String author;

    @Min(value = 1000, message = "Publication year must be 1000 or later")
    @Max(value = 9999, message = "Publication year must be 9999 or earlier")
    private int publicationYear;

    @NotNull(message = "ISBN is mandatory")
    @NotBlank(message = "ISBN cannot be blank")
    @Size(max = 20, message = "ISBN cannot be longer than 20 characters")
    @Pattern(regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$",
            message = "Invalid ISBN format")
    private String isbn;
}
