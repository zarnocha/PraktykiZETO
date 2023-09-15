package zeto.praktyki.User.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zeto.praktyki.User.UserEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SimpleUserDTO {

    private long id;

    private String firstName;

    private String lastName;

    public SimpleUserDTO(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
    }

}