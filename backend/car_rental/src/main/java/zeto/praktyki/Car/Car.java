package zeto.praktyki.Car;

import jakarta.persistence.*;
import lombok.Data;

@Data
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
    private String drive;
    private long price;
    private String licensePlate;
    private int seats;
    private String description;
    private double value;
    private long mileage;
    private boolean available;
}
