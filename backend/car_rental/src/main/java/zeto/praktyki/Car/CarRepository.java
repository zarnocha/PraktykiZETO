package zeto.praktyki.Car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car,Long>, PagingAndSortingRepository<Car, Long> {
    Car findAllByAvailable(boolean available);
}
