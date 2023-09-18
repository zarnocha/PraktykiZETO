package zeto.praktyki.Rent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.ws.rs.NotFoundException;
import zeto.praktyki.Car.CarEntity;
import zeto.praktyki.Car.CarService;
import zeto.praktyki.Rent.RentDTO.AddRentDTO;
import zeto.praktyki.Rent.RentDTO.RentDTO;
import zeto.praktyki.Rent.RentDTO.RentListQueryParamsDTO;
import zeto.praktyki.User.UserEntity;
import zeto.praktyki.User.UserService;

@Service
public class RentService {
    @Autowired
    public RentRepository rentRepository;

    @Autowired
    public UserService userService;

    @Autowired
    public CarService carService;

    @Autowired
    public RentRepositoryCQ rentRepositoryCQ;

    public List<RentEntity> getByCar(CarEntity car) {
        return rentRepository.findByCar(car);
    }

    public RentEntity getRentById(Long id) throws NotFoundException {
        Optional<RentEntity> rent = rentRepository.findById(id);
        if (rent.isPresent()) {
            return rent.get();
        } else {
            throw new NotFoundException("Nie ma wypożyczenia o takim ID.");
        }
    }

    static public double calculatePrice(Double price, Integer year) {
        Integer carYears = LocalDate.now().getYear() - year;
        if (carYears < 30) {
            carYears = 30;
        }
        Double b = price * 0.002;
        Double c = (105 - carYears) / 100.0;
        return b * c;
    }

    static public double calculatePriceForDate(Double dayPrice, LocalDateTime from, LocalDateTime to) {
        Long days = from.until(to, ChronoUnit.DAYS);
        Double a = Math.pow(0.999, days);
        return dayPrice * a;
    }

    static public double calculateWholePriceForDate(Double dayPrice, LocalDateTime from, LocalDateTime to) {
        Long days = from.until(to, ChronoUnit.DAYS);
        Double a = Math.pow(0.999, days);
        return dayPrice * a * (days + 1);
    }

    static public double calculatePriceForDateForCar(Double carValue, Integer productionYear, LocalDateTime startTime,
            LocalDateTime endTime) {
        Double priceForCar = calculatePrice(carValue, productionYear);
        return calculatePriceForDate(priceForCar, startTime, endTime);
    }

    static public double calculateWholePriceForDateForCar(Double carValue, Integer productionYear,
            LocalDateTime startTime,
            LocalDateTime endTime) {
        Double priceForCar = calculatePrice(carValue, productionYear);
        return calculateWholePriceForDate(priceForCar, startTime, endTime);
    }

    public RentDTO addRent(AddRentDTO rent) {
        CarEntity car = carService.getCarById(rent.getCarId());

        if (rent.getStartTime() == null || rent.getEndTime() == null) {
            throw new IllegalArgumentException(
                    "Do wynajęcia pojazdu potrzebny jest okres jego wynajmu.");
        }

        if (rent.getEndTime().isBefore(rent.getStartTime())) {
            throw new IllegalArgumentException(
                    "Nie można zwrócić samochodu w czasie wcześniejszym niż jego odebranie.");
        }

        if (rent.getStartTime().isAfter(rent.getEndTime())) {
            throw new IllegalArgumentException(
                    "Nie można odebrać samochodu w czasie późniejszym niż jego zwrot.");
        }

        UserEntity user = userService.getUserById(rent.getUserId());
        Double wholePrice = calculateWholePriceForDateForCar(car.getValue(), car.getProductionYear(),
                rent.getStartTime(),
                rent.getEndTime());

        RentEntity rentEntity = new RentEntity(rent.getStartTime(), rent.getEndTime(), wholePrice, car, user);
        RentEntity savedEntity = rentRepository.save(rentEntity);

        return new RentDTO(savedEntity);
    }

    public List<RentDTO> getRentListDTO(RentListQueryParamsDTO rentListQueryParamsDTO) {
        return rentRepositoryCQ.findRentByTimeAndPriceAndUserAndCarAndReturn(rentListQueryParamsDTO);
    }

    public String markCarRentTake(Long id, LocalDateTime startTime) throws Exception {
        RentEntity rent = getRentById(id);

        if (rent.getActualEndTime() != null && rent.getActualEndTime().isBefore(startTime)) {
            throw new IllegalArgumentException("Czas odebrania auta nie może być po jego zwrocie!");
        }

        rent.setActualStartTime(startTime);
        rentRepository.save(rent);

        return "Pomyślnie oznaczono czas odebrania samochodu.";
    }

    public String markCarRentReturn(Long id, LocalDateTime endTime) throws Exception {
        RentEntity rent = getRentById(id);

        if (rent.getActualStartTime() == null) {
            throw new IllegalArgumentException("Nie można oznaczyć zwrotu samochodu, ponieważ nie został on odebrany.");
        }

        if (endTime.isBefore(rent.getActualStartTime())) {
            throw new IllegalArgumentException(
                    "Nie można oznaczyć zwrotu samochodu w czasie wcześniejszym niż jego odebranie.");
        }

        rent.setActualEndTime(endTime);
        rentRepository.save(rent);

        return "Pomyślnie oznaczono czas zwrócenia samochodu.";
    }

    // public List<RentDTO> getRentsDTO() {
    // List<RentEntity> listRentEntity = rentRepository.findAll();
    // return listRentEntity.stream()
    // .map((rent) -> new RentDTO(rent))
    // .collect(Collectors.toList());
    // }

}
