package zeto.praktyki.Car.CarDTO;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarBrandModelDTO {
    String brand;
    Set<String> models;
}
