package zeto.praktyki.Car.CarDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zeto.praktyki.Car.CarEntity;
import zeto.praktyki.Car.CarEnums.*;
import zeto.praktyki.Rent.RentService;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private long id;
    private String brand;
    private String model;
    private Integer productionYear;
    private float fuelConsumption;
    private float engineCapacity;
    private Drive drive;
    private int seats;
    private Gearbox gearbox;
    @JsonIgnore
    private double value;
    private double dayPrice;
    private String description;
    private String picture;

    public CarDTO(CarEntity car) {
        this.id = car.getId();
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.productionYear = car.getProductionYear();
        this.fuelConsumption = car.getFuelConsumption();
        this.engineCapacity = car.getEngineCapacity();
        this.drive = car.getDrive();
        this.seats = car.getSeats();
        this.gearbox = car.getGearbox();
        this.picture = car.getPicture();
        this.description = car.getDescription();
        this.value = car.getValue();
        this.dayPrice = RentService.calculatePrice(car.getValue(), car.getProductionYear());
    }

    public CarDTO(long id, String brand, String model, Integer productionYear, float fuelConsumption,
            float engineCapacity, Drive drive, int seats, Gearbox gearbox, String description, String picture,
            Double value) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.productionYear = productionYear;
        this.fuelConsumption = fuelConsumption;
        this.engineCapacity = engineCapacity;
        this.drive = drive;
        this.seats = seats;
        this.gearbox = gearbox;
        this.description = description;
        this.picture = picture;
        this.value = value;
        this.dayPrice = RentService.calculatePrice(value, productionYear);
    }
}