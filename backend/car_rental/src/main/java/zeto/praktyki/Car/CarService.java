package zeto.praktyki.Car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import zeto.praktyki.Car.CarDTO.CarFilterDTO;
import zeto.praktyki.Car.CarDTO.CarDTO;
import zeto.praktyki.Car.CarDTO.CarListQueryParamsDTO;
import zeto.praktyki.Rent.RentService;

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

    public CarDTO getCarDTOById(long id) {
        if (carRepository.findById(id).isPresent()) {
            CarEntity car = carRepository.findById(id).get();
            return new CarDTO(car);
        } else {
            throw new ResponseStatusException(HttpStatus.valueOf(403), "Nie ma takiego samochodu w bazie danych.");
        }
    }

    public CarEntity addCar(CarEntity car) {
        return carRepository.save(car);
    }

    public List<CarDTO> getCarListDTO(
            CarListQueryParamsDTO carListQueryParams) {

        List<CarDTO> cars = carRepositoryCQ
                .findCarByBrandAndModelAndHorsePowerAndDriveAndGearbox(carListQueryParams);

        if (carListQueryParams.getFrom() != null && carListQueryParams.getTo() != null) {
            for (CarDTO car : cars) {
                car.setDayPrice(RentService.calculatePriceForDate(car.getDayPrice(), carListQueryParams.getFrom(),
                        carListQueryParams.getTo()));
            }
        }
        return cars;
    }

    public void deleteCarById(Long id) {
        if (carRepository.findById(id).isPresent()) {
            CarEntity carToDelete = carRepository.findById(id).get();
            carRepository.delete(carToDelete);
        } else {
            throw new ResponseStatusException(HttpStatus.OK, "Nie znaleziono samochodu o takim id!");
        }
    }

    public CarFilterDTO getCarFilterDTO() {
        return carRepositoryCQ.getCarFilterDTO();
    }

    public CarEntity getCarById(long id) {
        if (carRepository.findById(id).isPresent()) {
            CarEntity car = carRepository.findById(id).get();
            return car;
        } else {
            throw new ResponseStatusException(HttpStatus.valueOf(403), "Nie ma takiego samochodu w bazie danych.");
        }
    }

}
