package zeto.praktyki.Rent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    static public double calculatePrice(LocalDateTime from, LocalDateTime to, Double price, Integer year) {
        Long hours = from.until(to, ChronoUnit.HOURS);
        System.out.println(hours);
        Integer carYears = LocalDate.now().getYear() - year;
        if (carYears < 30) {
            carYears = 30;
        }
        Double a = Math.pow(0.999, hours);
        Double b = price * 0.0003;
        Double c = (105 - carYears) / 100.0;
        return a * b * c * hours;
        // return Math.pow(0.999, hours) * this.price * 0.0003 * ((105 - carYears) /
        // 100.0) * hours;
    }
}
