package zeto.praktyki.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CarService {
    @Autowired
    public CarRepository carRepository;


    public List<Car> getCars(){
        return carRepository.findAll();
    }

    public Car getCarById (long id){
        if(carRepository.findById(id).isPresent()){
            return carRepository.findById(id).get();
        } else {
            throw new ResponseStatusException(HttpStatus.valueOf(403), "Nie ma takiego użytkownika w bazie danych.");
        }

    }



    public Car addCar(Car car){
        return carRepository.save(car);
    }

}
