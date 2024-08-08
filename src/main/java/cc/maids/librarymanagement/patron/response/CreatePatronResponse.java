package cc.maids.librarymanagement.patron.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatronResponse {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
}
