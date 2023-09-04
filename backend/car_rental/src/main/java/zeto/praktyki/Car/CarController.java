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

    @GetMapping(path = "/test", produces = "application/json")
    public ResponseEntity<Object> getCarsTest(@RequestParam(name = "brand", required = false) String brand,
            @RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "horsePowerFrom", required = false) Integer horsePowerFrom,
            @RequestParam(name = "horsePowerTo", required = false) Integer horsePowerTo,
            @RequestParam(name = "gearbox", required = false) Gearbox gearbox,
            @RequestParam(name = "drive", required = false) Drive drive,
            @RequestParam(name = "from", required = true) LocalDateTime from,
            @RequestParam(name = "to", required = true) LocalDateTime to) {
        try {
            System.out.println("typy: " + from.getClass().getCanonicalName() + to.getClass().getCanonicalName());
            return new ResponseEntity<>(
                    carService.getCarByBrandAndModelAndHorsePowerAndDriveAndGearboxAndTime(brand, model,
                            horsePowerFrom, horsePowerTo, gearbox, drive, from, to),
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Object>("Błąd: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // @DeleteMapping(path = "/delete/{id}")
}
