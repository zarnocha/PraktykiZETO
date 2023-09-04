package zeto.praktyki.Rent;

import jakarta.persistence.*;
import lombok.Data;
import zeto.praktyki.Car.Car;
import zeto.praktyki.User.User;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "rent")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean actualStartTime;
    private LocalDateTime actualEndTime;
    private double price;

    @ManyToOne
    @JoinColumn
    private Car car;

    @ManyToOne
    @JoinColumn
    private User user;

}
