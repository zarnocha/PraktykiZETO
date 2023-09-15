package zeto.praktyki.User.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRegisterDTO {
    private String login;

    private String password;

    private String firstName;

    private String lastName;

}
