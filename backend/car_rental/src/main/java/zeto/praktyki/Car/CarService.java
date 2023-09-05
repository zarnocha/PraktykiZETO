package zeto.praktyki.Car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import zeto.praktyki.Car.CarDTO.CarListDTO;
import zeto.praktyki.Car.CarDTO.CarListQueryParamsDTO;
import zeto.praktyki.Rent.RentService;

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

    public List<CarListDTO> getCarListDTOByBrandAndModelAndHorsePowerAndDriveAndGearboxAndTime(
            CarListQueryParamsDTO carListQueryParams) {
        List<CarEntity> cars = carRepositoryCQ
                .findCarByBrandAndModelAndHorsePowerAndDriveAndGearbox(carListQueryParams);

        List<CarListDTO> carsDTO = new ArrayList<CarListDTO>();

        for (CarEntity car : cars) {
            carsDTO.add(new CarListDTO(car, carListQueryParams.getFrom(), carListQueryParams.getTo()));
        }

        return carsDTO;
    }

    public void deleteCarById(Long id) {
        if (carRepository.findById(id).isPresent()) {
            CarEntity carToDelete = carRepository.findById(id).get();
            carRepository.delete(carToDelete);
        } else {
            throw new ResponseStatusException(HttpStatus.OK, "Nie znaleziono samochodu o takim id!");
        }
    }

}
