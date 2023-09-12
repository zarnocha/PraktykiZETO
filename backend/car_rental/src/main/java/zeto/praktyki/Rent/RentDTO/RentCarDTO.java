package zeto.praktyki.Rent.RentDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zeto.praktyki.Car.CarEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentCarDTO {
    private long id;
    private String brand;
    private String model;
    private String picture;

    public RentCarDTO(CarEntity car) {
        this.id = car.getId();
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.picture = car.getPicture();
    }

}
