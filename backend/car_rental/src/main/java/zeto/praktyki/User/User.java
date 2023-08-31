package zeto.praktyki.User;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "User_")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String creditCardNuber;
    private LocalDate creditCardExpDate;
    private String cvv;
    private Boolean isAdmin;
}
