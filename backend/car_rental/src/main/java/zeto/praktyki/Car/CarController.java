package zeto.praktyki.Car;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.security.auth.message.AuthException;
import zeto.praktyki.Car.CarDTO.CarFilterDTO;
import zeto.praktyki.Car.CarDTO.CarDTO;
import zeto.praktyki.Car.CarDTO.CarListQueryParamsDTO;
import zeto.praktyki.Car.CarDTO.CarPriceDTO;
import zeto.praktyki.Car.CarDTO.EditCarDTO;
import zeto.praktyki.Rent.RentService;
import zeto.praktyki.Rent.RentDTO.AddRentDTO;
import zeto.praktyki.User.Auth.JwtUtil;
import zeto.praktyki.User.Auth.JwtUtil.WhoCanAccess;

@RequestMapping("/api/car")
@RestController
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CarController {
    @Autowired
    CarService carService;

    @Autowired
    CarRepository carRepository;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping(path = "{id}")
    public CarDTO getById(@PathVariable long id)
            throws AuthException {
        CarEntity foundCar = carService.getCarById(id);
        return new CarDTO(foundCar);
    }

    @GetMapping(path = "{id}/price")
    public CarPriceDTO getPriceForSpecificCar(@PathVariable long id, AddRentDTO addRentDTO) {
        CarEntity foundCar = carService.getCarById(id);

        Double wholePrice = RentService.calculateWholePriceForDateForCar(foundCar.getValue(),
                foundCar.getProductionYear(), addRentDTO.getStartTime(), addRentDTO.getEndTime());
        Double dayPrice = RentService.calculatePriceForDateForCar(foundCar.getValue(), foundCar.getProductionYear(),
                addRentDTO.getStartTime(), addRentDTO.getEndTime());

        CarPriceDTO carPriceDTO = new CarPriceDTO(wholePrice, dayPrice);

        return carPriceDTO;
    }

    @GetMapping(path = "filter")
    public List<CarDTO> getCarsFiltered(CarListQueryParamsDTO params) {
        return carService.getCarListDTO(params);
    }

    @GetMapping(path = "availableFilters")
    public CarFilterDTO getCarsFilters() {
        return carService.getCarFilterDTO();
    }

    @PostMapping(path = "add")
    public CarDTO addCar(@RequestBody CarEntity car, @RequestHeader("Authorization") String bearerToken)
            throws AuthException {
        jwtUtil.access(bearerToken, WhoCanAccess.ADMIN);
        CarEntity createdCar = carService.addCar(car);
        return new CarDTO(createdCar);
    }

    @DeleteMapping(path = "{id}")
    public String deleteCar(@PathVariable Long id, @RequestHeader("Authorization") String bearerToken)
            throws AuthException {
        jwtUtil.access(bearerToken, WhoCanAccess.ADMIN);
        carService.deleteCarById(id);
        return "Pomyślnie usunięto pojazd o ID: " + id;

    }

    @PatchMapping(path = "{id}")
    public CarDTO editCar(@PathVariable Long id, @RequestBody EditCarDTO editCarDTO,
            @RequestHeader("Authorization") String bearerToken) throws Exception {
        jwtUtil.access(bearerToken, WhoCanAccess.ADMIN);

        CarEntity foundCar = carService.getCarById(id);

        Field[] fields = EditCarDTO.class.getDeclaredFields();

        for (Field dtoField : fields) {
            try {
                Field entityField = CarEntity.class.getDeclaredField(dtoField.getName());

                dtoField.setAccessible(true);
                entityField.setAccessible(true);

                Object dtoValue = dtoField.get(editCarDTO);

                if (dtoValue != null) {
                    entityField.set(foundCar, dtoValue);
                }

            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new Exception("Nie udało się edytować auta.");
            }
        }

        carRepository.save(foundCar);
        return new CarDTO(foundCar);

    };
}
