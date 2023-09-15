package zeto.praktyki.Car.CarDTO;

import java.time.LocalDateTime;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zeto.praktyki.Car.CarEnums.Drive;
import zeto.praktyki.Car.CarEnums.Gearbox;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarListQueryParamsDTO {
    @Nullable
    String brand;
    @Nullable
    String model;
    @Nullable
    Integer horsePowerFrom;
    @Nullable
    Integer horsePowerTo;
    @Nullable
    Gearbox gearbox;
    @Nullable
    Drive drive;
    @Nullable
    LocalDateTime from;
    @Nullable
    LocalDateTime to;
    @Nullable
    Boolean available;
}
