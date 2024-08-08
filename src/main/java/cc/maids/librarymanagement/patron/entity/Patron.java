package cc.maids.librarymanagement.patron.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Column(nullable = false, unique = true)
    @Size(max = 255, message = "Email must be at most 255 characters")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{10,14}$", message = "Phone number should be valid and contain 10-14 digits, with an optional '+' at the start")
    private String phoneNumber;
}
