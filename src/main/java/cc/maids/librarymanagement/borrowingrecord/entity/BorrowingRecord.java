package cc.maids.librarymanagement.borrowingrecord.entity;

import cc.maids.librarymanagement.book.entity.Book;
import cc.maids.librarymanagement.patron.entity.Patron;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    @ManyToOne
    @JoinColumn(name = "patron_id", nullable = false)
    private Patron patron;
    private LocalDateTime borrowingDate;
    private LocalDateTime returnDate;
}
