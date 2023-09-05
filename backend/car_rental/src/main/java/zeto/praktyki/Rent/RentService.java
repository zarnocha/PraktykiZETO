package zeto.praktyki.Rent;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zeto.praktyki.Car.CarEntity;

@Service
public class RentService {
    @Autowired
    public RentRepository rentRepository;

    @Autowired
    public RentRepositoryCQ rentRepositoryCQ;

    public List<RentEntity> getByCar(CarEntity car) {
        return rentRepository.findByCar(car);
    }
}
