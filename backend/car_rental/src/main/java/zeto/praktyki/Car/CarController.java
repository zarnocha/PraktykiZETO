package zeto.praktyki.Car;

import java.time.LocalDateTime;
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

import zeto.praktyki.Car.CarDTO.CarListQueryParamsDTO;
import zeto.praktyki.Car.CarEnums.Drive;
import zeto.praktyki.Car.CarEnums.Gearbox;

@RequestMapping("/api/car")
@RestController
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
    public ResponseEntity<Object> getCarsFiltered(@RequestParam(name = "brand", required = false) String brand,
            @RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "horsePowerFrom", required = false) Integer horsePowerFrom,
            @RequestParam(name = "horsePowerTo", required = false) Integer horsePowerTo,
            @RequestParam(name = "gearbox", required = false) Gearbox gearbox,
            @RequestParam(name = "drive", required = false) Drive drive,
            @RequestParam(name = "from", required = true) LocalDateTime from,
            @RequestParam(name = "to", required = true) LocalDateTime to,
            @RequestParam(name = "available", required = false, defaultValue = "true") Boolean available) {
        try {
            CarListQueryParamsDTO argument = new CarListQueryParamsDTO(brand, model, horsePowerFrom, horsePowerTo,
                    gearbox, drive, from, to, available);
            return new ResponseEntity<>(
                    carService.getCarListDTOByBrandAndModelAndHorsePowerAndDriveAndGearboxAndTime(argument),
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
