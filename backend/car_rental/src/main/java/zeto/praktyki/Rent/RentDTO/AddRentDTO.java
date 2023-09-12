package zeto.praktyki.Rent.RentDTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRentDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long carId;
    private Long userId;

    public AddRentDTO(LocalDateTime startTime, LocalDateTime endTime, Long carId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.carId = carId;
    }

}
