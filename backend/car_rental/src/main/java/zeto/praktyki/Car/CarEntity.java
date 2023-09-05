package zeto.praktyki.Car;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zeto.praktyki.Car.CarEnums.Drive;
import zeto.praktyki.Car.CarEnums.Gearbox;
import zeto.praktyki.Rent.RentEntity;
import zeto.praktyki.User.UserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "car")
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private Integer productionYear;
    @Column(nullable = false)
    private float fuelConsumption;
    @Column(nullable = false)
    private float engineCapacity;
    @Column(nullable = false)
    private Integer horsePower;
    @Column(nullable = false)
    private Drive drive;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private String licensePlate;
    @Column(nullable = false)
    private int seats;
    @Column(nullable = true)
    private String description;
    @Column(nullable = false)
    private double value;
    @Column(nullable = false)
    private long mileage;
    @Column(nullable = false)
    private Gearbox gearbox;
    @Lob
    @Column(nullable = true)
    String picture;

    @JsonIgnore
    @OneToMany(mappedBy = "car")
    private Set<RentEntity> rents;

    @ManyToOne
    @JoinColumn
    // @Column(nullable = false) TODO: zmienić na nullable true
    private UserEntity added_by;

    protected CarEntity(long id, String brand, String model, Integer productionYear, float fuelConsumption,
            float engineCapacity, Integer horsePower, Drive drive, long price, String licensePlate, int seats,
            String description, double value, long mileage, Gearbox gearbox, String picture) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.productionYear = productionYear;
        this.fuelConsumption = fuelConsumption;
        this.engineCapacity = engineCapacity;
        this.horsePower = horsePower;
        this.drive = drive;
        this.price = price;
        this.licensePlate = licensePlate;
        this.seats = seats;
        this.description = description;
        this.value = value;
        this.mileage = mileage;
        this.rents = new HashSet<>();
        this.added_by = null; // TODO jak bedzie token to zmienić
        this.gearbox = gearbox;
        this.picture = picture;
    }

    public double calculatePrice(LocalDateTime from, LocalDateTime to) {
        Long hours = from.until(to, ChronoUnit.HOURS);
        System.out.println(hours);
        Integer carYears = LocalDate.now().getYear() - this.getProductionYear();
        if (carYears < 30) {
            carYears = 30;
        }
        Double a = Math.pow(0.999, hours);
        Double b = this.price * 0.0003;
        Double c = (105 - carYears) / 100.0;
        return a * b * c * hours;
        // return Math.pow(0.999, hours) * this.price * 0.0003 * ((105 - carYears) /
        // 100.0) * hours;
    }
}
