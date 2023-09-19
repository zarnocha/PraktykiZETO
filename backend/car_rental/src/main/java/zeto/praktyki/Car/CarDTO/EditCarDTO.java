package zeto.praktyki.Car.CarDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zeto.praktyki.Car.CarEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditCarDTO {
    private String brand;
    private String model;
    private String licensePlate;
    private String description;
    private double value;
    private long mileage;
    private String picture;

    public EditCarDTO(CarEntity foundCar) {
        this.brand = foundCar.getBrand();
        this.model = foundCar.getModel();
        this.licensePlate = foundCar.getLicensePlate();
        this.description = foundCar.getDescription();
        this.value = foundCar.getValue();
        this.mileage = foundCar.getMileage();
        this.picture = foundCar.getPicture();
    }
}
