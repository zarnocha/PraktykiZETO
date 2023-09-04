package zeto.praktyki.Rent;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import zeto.praktyki.Car.CarEntity;

@Repository
public interface RentRepository extends JpaRepository<RentEntity, Long> {
    List<RentEntity> findByCar(CarEntity car);
    // Page<RentEntity> findAllByStartTime(LocalDateTime startTime, Pageable
    // pagable);

    // Page<RentEntity> findAllByEndTime(LocalDateTime endTime, Pageable pagable);

    // Page<RentEntity> findAllByStartTimeBetween(LocalDateTime startTime,
    // LocalDateTime endTime, Pageable pagable);

    // Page<RentEntity> findAllByEndTimeBetween(LocalDateTime startTime,
    // LocalDateTime endTime, Pageable pagable);

    // Page<RentEntity> findAllByStartTimeAndEndTimeBetween(LocalDateTime startTime,
    // LocalDateTime endTime,
    // Pageable pagable);
}
