package zeto.praktyki.Rent;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zeto.praktyki.Car.CarEntity;
import zeto.praktyki.Car.CarRepositoryCQ;

@Service
public class RentService {
    @Autowired
    public RentRepository rentRepository;

    @Autowired
    public RentRepositoryCQ rentRepositoryCQ;

    public List<RentEntity> getByCar(CarEntity car) {
        return rentRepository.findByCar(car);
    }

    public Boolean isCarAvailableAtTime(CarEntity car, LocalDateTime from, LocalDateTime to) {
        return rentRepositoryCQ.findUnavailableRentsByDate(car, from, to).isEmpty();
    }

}
