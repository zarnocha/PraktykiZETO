package zeto.praktyki.User.UserDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zeto.praktyki.User.UserEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    private long id;
    private String login;

    private String firstName;
    private String lastName;

    private String creditCardNumber;
    private LocalDate creditCardExpDate;
    private String cvv;

    private String email;
    private String phoneNumber;

    public UserProfileDTO(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.login = userEntity.getLogin();
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
        this.creditCardNumber = userEntity.getCreditCardNumber();
        this.creditCardExpDate = userEntity.getCreditCardExpDate();
        this.cvv = userEntity.getCvv();
        this.email = userEntity.getEmail();
        this.phoneNumber = userEntity.getPhoneNumber();
    }

}
