package zeto.praktyki.Rent;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zeto.praktyki.Car.CarEntity;
import zeto.praktyki.Rent.RentDTO.AddRentDTO;
import zeto.praktyki.User.UserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rent")
public class RentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = true)
    private LocalDateTime actualStartTime;

    @Column(nullable = true)
    private LocalDateTime actualEndTime;

    @Column(nullable = false)
    private double price;

    // @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private CarEntity car;

    // @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "user__id", nullable = false)
    private UserEntity user;

    public RentEntity(LocalDateTime startTime, LocalDateTime endTime, double price, CarEntity car, UserEntity user) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.car = car;
        this.user = user;
    }
}
