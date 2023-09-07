package zeto.praktyki.Car.CarDTO;

import java.util.List;

import jakarta.annotation.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarFilterDTO {
    @Nullable
    Integer minHorsepower;
    @Nullable
    Integer maxHorsepower;
    @Nullable
    List<CarBrandModelDTO> brands;

    public CarFilterDTO(Integer minHorsepower, Integer maxHorsepower) {
        this.minHorsepower = minHorsepower;
        this.maxHorsepower = maxHorsepower;
    }
}
