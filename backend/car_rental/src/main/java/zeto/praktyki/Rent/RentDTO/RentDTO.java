package zeto.praktyki.Rent.RentDTO;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import zeto.praktyki.Car.CarEntity;
import zeto.praktyki.Rent.RentEntity;
import zeto.praktyki.Rent.RentService;
import zeto.praktyki.User.UserEntity;
import zeto.praktyki.User.UserDTO.SimpleUserDTO;

@Data
// @AllArgsConstructor
@NoArgsConstructor
public class RentDTO {

    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime actualStartTime;
    private LocalDateTime actualEndTime;
    private double price;
    private double dayPrice;
    private RentCarDTO car;
    private SimpleUserDTO user;

    public RentDTO(RentEntity rentEntity) {
        this.id = rentEntity.getId();
        this.startTime = rentEntity.getStartTime();
        this.endTime = rentEntity.getEndTime();
        this.actualStartTime = rentEntity.getActualStartTime();
        this.actualEndTime = rentEntity.getActualEndTime();
        this.price = rentEntity.getPrice();
        this.car = new RentCarDTO(rentEntity.getCar());
        this.user = new SimpleUserDTO(rentEntity.getUser());
        this.dayPrice = RentService.calculatePriceForDateForCar(rentEntity.getCar().getValue(),
                rentEntity.getCar().getProductionYear(), rentEntity.getStartTime(), rentEntity.getEndTime());
    }

    public RentDTO(Long id, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime actualStartTime,
            LocalDateTime actualEndTime, double price, CarEntity car, UserEntity user) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.actualStartTime = actualStartTime;
        this.actualEndTime = actualEndTime;
        this.price = price;
        this.car = new RentCarDTO(car);
        this.user = new SimpleUserDTO(user);
        this.dayPrice = RentService.calculatePriceForDateForCar(
                car.getValue(), car.getProductionYear(), startTime, endTime);
    }

}