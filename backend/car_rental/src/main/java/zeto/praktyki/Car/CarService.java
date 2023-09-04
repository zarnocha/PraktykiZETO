package zeto.praktyki.Car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import zeto.praktyki.Car.CarEnums.Drive;
import zeto.praktyki.Car.CarEnums.Gearbox;
import zeto.praktyki.Rent.RentEntity;
import zeto.praktyki.Rent.RentRepository;
import zeto.praktyki.Rent.RentService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {
    @Autowired
    public CarRepository carRepository;
    @Autowired
    public CarRepositoryCQ carRepositoryCQ;
    @Autowired
    public RentService rentService;

    public List<CarEntity> getCars() {
        return carRepository.findAll();
    }

    public CarEntity getCarById(long id) {
        if (carRepository.findById(id).isPresent()) {
            return carRepository.findById(id).get();
        } else {
            throw new ResponseStatusException(HttpStatus.valueOf(403), "Nie ma takiego użytkownika w bazie danych.");
        }
    }

    public CarEntity addCar(CarEntity car) {
        return carRepository.save(car);
    }

    public List<CarEntity> getCarByBrandAndModelAndHorsePowerAndDriveAndGearboxAndTime(String brand, String model,
            Integer horsePowerFrom, Integer horsePowerTo, Gearbox gearbox, Drive drive, LocalDateTime rentFrom,
            LocalDateTime rentTo) {
        List<CarEntity> cars = carRepositoryCQ.findCarByBrandAndModelAndHorsePowerAndDriveAndGearbox(brand, model,
                horsePowerFrom,
                horsePowerTo, gearbox, drive);

        System.out.println("from: " + rentFrom + "\nto: " + rentTo);

        List<CarEntity> carsCopy = new ArrayList<CarEntity>(cars);

        for (CarEntity car : carsCopy) {
            System.out.println("weszlo w petle");
            if (!rentService.isCarAvailableAtTime(car, rentFrom, rentTo)) {
                cars.remove(car);
            }
        }

        return cars;
    }

    // https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/criteria-api-between-operator.html

    // public boolean isCarAvailableAtTime(CarEntity car, LocalDateTime from,
    // LocalDateTime to) {
    // List<RentEntity> rents = rentService.getByCar(car);
    // for (RentEntity rent : rents) {
    // Boolean isStartTimeInBetween = rent.getStartTime().isAfter(from) &&
    // rent.getStartTime().isBefore(to);
    // }
    // }

}
