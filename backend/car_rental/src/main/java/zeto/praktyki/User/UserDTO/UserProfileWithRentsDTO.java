package zeto.praktyki.User.UserDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zeto.praktyki.Rent.RentDTO.RentDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileWithRentsDTO {
    UserProfileDTO profile;
    List<RentDTO> rents;
}
