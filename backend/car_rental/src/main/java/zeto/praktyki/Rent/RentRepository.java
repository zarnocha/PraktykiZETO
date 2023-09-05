package zeto.praktyki.Rent;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import zeto.praktyki.Car.CarEntity;

public interface RentRepository extends JpaRepository<RentEntity, Long> {
    List<RentEntity> findByCar(CarEntity car);
}
