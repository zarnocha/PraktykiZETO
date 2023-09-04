package zeto.praktyki.Car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<Object> addCar(@RequestBody Car car) {
        try {
            return new ResponseEntity<>(carService.addCar(car), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Object>("Błąd: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // @DeleteMapping(path = "/delete/{id}")
}
