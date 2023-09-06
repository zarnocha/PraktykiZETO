package zeto.praktyki.Rent;

import jakarta.persistence.*;
import lombok.Data;
import zeto.praktyki.Car.CarEntity;
import zeto.praktyki.User.UserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
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

    @ManyToOne
    @JoinColumn(nullable = false)
    private CarEntity car;

    @ManyToOne
    @JoinColumn(nullable = false)
    private UserEntity user;

}
