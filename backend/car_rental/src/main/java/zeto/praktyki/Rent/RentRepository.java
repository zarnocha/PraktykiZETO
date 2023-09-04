package zeto.praktyki.Rent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepository extends JpaRepository<Rent,Long>{
    Page<Rent> findAllByStartTime(LocalDateTime startTime, Pageable pagable);
    Page<Rent> findAllByEndTime(LocalDateTime endTime, Pageable pagable);
    Page<Rent> findAllByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime, Pageable pagable);
    Page<Rent> findAllByEndTimeBetween(LocalDateTime startTime, LocalDateTime endTime, Pageable pagable);
    Page<Rent> findAllByStartTimeAndEndTimeBetween(LocalDateTime startTime, LocalDateTime endTime, Pageable pagable);
}
