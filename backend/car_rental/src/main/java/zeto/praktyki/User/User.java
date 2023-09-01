package zeto.praktyki.User;

import jakarta.persistence.*;
import lombok.Data;
import zeto.praktyki.Car.Car;
import zeto.praktyki.Rent.Rent;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

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

    @OneToMany(mappedBy = "user")
    private Set<Rent> rents;

    @OneToMany(mappedBy = "added_by")
    private Set<Car> cars;

}
