package zeto.praktyki.Rent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zeto.praktyki.Car.CarEntity;
import zeto.praktyki.Car.CarRepository;
import zeto.praktyki.Car.CarService;
import zeto.praktyki.Rent.RentDTO.AddRentDTO;
import zeto.praktyki.Rent.RentDTO.RentDTO;
import zeto.praktyki.User.UserEntity;
import zeto.praktyki.User.UserRepository;
import zeto.praktyki.User.UserService;

@Service
public class RentService {
    @Autowired
    public RentRepository rentRepository;

    @Autowired
    public UserService userService;

    @Autowired
    public CarService carService;

    @Autowired
    public RentRepositoryCQ rentRepositoryCQ;

    public List<RentEntity> getByCar(CarEntity car) {
        return rentRepository.findByCar(car);
    }

    static public double calculatePrice(Double price, Integer year) {
        Integer carYears = LocalDate.now().getYear() - year;
        if (carYears < 30) {
            carYears = 30;
        }
        Double b = price * 0.002;
        Double c = (105 - carYears) / 100.0;
        return b * c;
    }

    static public double calculatePriceForDate(Double dayPrice, LocalDateTime from, LocalDateTime to) {
        Long days = from.until(to, ChronoUnit.DAYS);
        Double a = Math.pow(0.999, days);
        return dayPrice * a;
    }

    static public double calculatePriceForDateForCar(Double carValue, Integer productionYear, LocalDateTime startTime,
            LocalDateTime endTime) {
        Double priceForCar = calculatePrice(carValue, productionYear);
        return calculatePriceForDate(priceForCar, startTime, endTime);
    }

    public RentDTO addRent(AddRentDTO rent) {
        CarEntity car = carService.getCarById(rent.getCarId());

        UserEntity user = userService.getUserById(rent.getUserId());
        Double wholePrice = calculatePriceForDateForCar(car.getValue(), car.getProductionYear(), rent.getStartTime(),
                rent.getEndTime());

        RentEntity rentEntity = new RentEntity(rent.getStartTime(), rent.getEndTime(), wholePrice, car, user);
        RentEntity savedEntity = rentRepository.save(rentEntity);
        return new RentDTO(savedEntity);
    }

    public List<RentDTO> getRentsDTO() {
        List<RentEntity> listRentEntity = rentRepository.findAll();
        return listRentEntity.stream()
                .map((rent) -> new RentDTO(rent))
                .collect(Collectors.toList());
    }

}
