package zeto.praktyki.Car;

import java.util.List;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import zeto.praktyki.Car.CarDTO.CarBrandModelDTO;
import zeto.praktyki.Car.CarDTO.CarFilterDTO;
import zeto.praktyki.Car.CarDTO.CarListDTO;
import zeto.praktyki.Car.CarDTO.CarListQueryParamsDTO;

@RequestMapping("/api/car")
@RestController
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CarController {
    @Autowired
    CarService carService;

    @GetMapping(path = "/all")
    // @GetMapping(path = "/all", produces = "application/json")
    public List<CarEntity> getCars() {
        return carService.getCars();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public CarEntity getById(@PathVariable long id) {
        return carService.getCarById(id);
    }

    @PostMapping(path = "/add", consumes = "application/json")
    public CarEntity addCar(@RequestBody CarEntity car) {
        return carService.addCar(car);
    }

    @GetMapping(path = "/filter", produces = "application/json")
    public List<CarListDTO> getCarsFiltered(CarListQueryParamsDTO params) {
        return carService.getCarListDTOByBrandAndModelAndHorsePowerAndDriveAndGearboxAndTime(params);
    }

    @GetMapping(path = "/availableFilters", produces = "application/json")
    public CarFilterDTO getCarsFiltered() {
        return carService.getCarFilterDTO();
    }

    // @DeleteMapping(path = "/{id}")
    // public ResponseEntity<Object> deleteCar() {
    // try {
    // carService.deleteCarById();
    // } catch (Exception e) {
    // e.printStackTrace();
    // return new ResponseEntity<Object>("Błąd: " + e.getMessage(),
    // HttpStatus.NOT_FOUND);
    // }
    // }
}
