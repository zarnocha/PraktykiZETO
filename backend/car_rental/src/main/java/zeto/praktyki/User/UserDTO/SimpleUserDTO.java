package zeto.praktyki.User.UserDTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zeto.praktyki.Car.CarEntity;
import zeto.praktyki.Rent.RentEntity;
import zeto.praktyki.User.UserEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SimpleUserDTO {

    private long id;

    private String firstName;

    private String lastName;

    public SimpleUserDTO(UserEntity userEntity) {
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
    }

}