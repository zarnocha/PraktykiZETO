package zeto.praktyki.User.UserDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {
    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private String creditCardNumber;

    private LocalDate creditCardExpDate;

    private String cvv;

    private String email;

    private String phoneNumber;
}
