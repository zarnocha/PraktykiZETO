package zeto.praktyki.Car;

import java.time.LocalDateTime;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import zeto.praktyki.Car.CarDTO.CarListQueryParamsDTO;
import zeto.praktyki.Car.CarEnums.Drive;
import zeto.praktyki.Car.CarEnums.Gearbox;

@RequestMapping("/api/car")
@RestController
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CarController {
    @Autowired
    CarService carService;

    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity<Object> getCars() {
        try {
            return new ResponseEntity<>(carService.getCars(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Object>("Błąd: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Object> getById(@PathVariable long id) {
        return new ResponseEntity<>(carService.getCarById(id), HttpStatus.OK);
    }

    @PostMapping(path = "/add", consumes = "application/json")
    public ResponseEntity<Object> addCar(@RequestBody CarEntity car) {
        try {
            return new ResponseEntity<>(carService.addCar(car), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Object>("Błąd: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/filter", produces = "application/json")
    public ResponseEntity<Object> getCarsFiltered(CarListQueryParamsDTO params) {
        try {
            return new ResponseEntity<>(
                    carService.getCarListDTOByBrandAndModelAndHorsePowerAndDriveAndGearboxAndTime(params),
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Object>("Błąd: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
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
