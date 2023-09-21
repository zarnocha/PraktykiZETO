package zeto.praktyki.User.UserDTO;

import java.time.LocalDate;

import jakarta.annotation.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditProfileDTO {
    @Nullable
    private String password;
    @Nullable
    private String firstName;
    @Nullable
    private String lastName;
    @Nullable
    private String creditCardNumber;
    @Nullable
    private LocalDate creditCardExpDate;
    @Nullable
    private String cvv;
    @Nullable
    private String email;
    @Nullable
    private String phoneNumber;
}
