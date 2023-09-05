package zeto.praktyki.Car.CarDTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zeto.praktyki.Car.CarEnums.Drive;
import zeto.praktyki.Car.CarEnums.Gearbox;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarListQueryParamsDTO {
    String brand;
    String model;
    Integer horsePowerFrom;
    Integer horsePowerTo;
    Gearbox gearbox;
    Drive drive;
    LocalDateTime from;
    LocalDateTime to;
    Boolean available;
}
