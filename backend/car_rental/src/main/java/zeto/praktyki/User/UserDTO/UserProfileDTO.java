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
    long id;
    String login;

    String firstName;
    String lastName;

    String creditCardNuber;
    LocalDate creditCardExpDate;
    String cvv;

    public UserProfileDTO(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.login = userEntity.getLogin();
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
        this.creditCardNuber = userEntity.getCreditCardNuber();
        this.creditCardExpDate = userEntity.getCreditCardExpDate();
        this.cvv = userEntity.getCvv();
    }

}
