package zeto.praktyki.Car;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zeto.praktyki.Car.CarEnums.Drive;
import zeto.praktyki.Car.CarEnums.Gearbox;
import zeto.praktyki.Rent.Rent;
import zeto.praktyki.User.User;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String brand;
    private String model;
    private Integer productionYear;
    private float fuelConsumption;
    private float engineCapacity;
    private Integer horsepower;
    private Drive drive;
    private long price; // TODO: zmienić na double
    private String licensePlate;
    private int seats;
    private String description;
    private double value;
    private long mileage;
    private boolean available;
    private Gearbox gearbox;
    @Lob
    String picture;

    @OneToMany(mappedBy = "car")
    private Set<Rent> rents;

    @ManyToOne
    @JoinColumn
    private User added_by;

    protected Car(long id, String brand, String model, Integer productionYear, float fuelConsumption,
            float engineCapacity, Integer horsepower, Drive drive, long price, String licensePlate, int seats,
            String description, double value, long mileage, Gearbox gearbox, String picture) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.productionYear = productionYear;
        this.fuelConsumption = fuelConsumption;
        this.engineCapacity = engineCapacity;
        this.horsepower = horsepower;
        this.drive = drive;
        this.price = price;
        this.licensePlate = licensePlate;
        this.seats = seats;
        this.description = description;
        this.value = value;
        this.mileage = mileage;
        this.available = true;
        this.rents = new HashSet<>();
        this.added_by = null; // TODO jak bedzie token to zmienić
        this.gearbox = gearbox;
        this.picture = picture;
    }
}
