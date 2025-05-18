package pages.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateTestUser {
    private String name;
    private String password;
    private String email;

}