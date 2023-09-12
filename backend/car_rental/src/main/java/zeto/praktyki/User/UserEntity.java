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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zeto.praktyki.Car.CarEntity;
import zeto.praktyki.Rent.RentEntity;
import zeto.praktyki.User.UserDTO.UserRegisterDTO;
import zeto.praktyki.User.UserDTO.AdminRegisterDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "User_")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
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

    @OneToMany(mappedBy = "addedBy")
    private Set<CarEntity> cars;

    public UserEntity(UserRegisterDTO userRegisterDTO) {
        this.login = userRegisterDTO.getLogin();
        this.password = userRegisterDTO.getPassword();
        this.firstName = userRegisterDTO.getFirstName();
        this.lastName = userRegisterDTO.getLastName();
        this.creditCardNuber = userRegisterDTO.getCreditCardNuber();
        this.creditCardExpDate = userRegisterDTO.getCreditCardExpDate();
        this.cvv = userRegisterDTO.getCvv();
        this.isAdmin = false;
    }

    public UserEntity(AdminRegisterDTO adminRegisterDTO) {
        this.login = adminRegisterDTO.getLogin();
        this.password = adminRegisterDTO.getPassword();
        this.firstName = adminRegisterDTO.getFirstName();
        this.lastName = adminRegisterDTO.getLastName();
        this.isAdmin = true;
    }

}
