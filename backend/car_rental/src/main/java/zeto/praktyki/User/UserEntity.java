package zeto.praktyki.User;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import zeto.praktyki.Car.CarEntity;
import zeto.praktyki.Rent.RentEntity;

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
