package zeto.praktyki.User;

import jakarta.persistence.*;
import lombok.Data;
import zeto.praktyki.Car.CarEntity;
import zeto.praktyki.Rent.RentEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "User_")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = true)
    private String creditCardNuber;

    @Column(nullable = true)
    private LocalDate creditCardExpDate;

    @Column(nullable = true)
    private String cvv;

    @Column(nullable = false)
    private Boolean isAdmin;

    @OneToMany(mappedBy = "user")
    private Set<RentEntity> rents;

    @OneToMany(mappedBy = "added_by")
    private Set<CarEntity> cars;

}
