package zeto.praktyki.Car.CarDTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zeto.praktyki.Car.CarEntity;
import zeto.praktyki.Car.CarEnums.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarListDTO {
    private long id;
    private String brand;
    private String model;
    private Integer productionYear;
    private float fuelConsumption;
    private float engineCapacity;
    private Drive drive;
    private int seats;
    private Gearbox gearbox;
    private double wholePrice;
    private String picture;

    public CarListDTO(CarEntity car, LocalDateTime from, LocalDateTime to) {
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
        this.wholePrice = car.calculatePrice(from, to);
    }
}
