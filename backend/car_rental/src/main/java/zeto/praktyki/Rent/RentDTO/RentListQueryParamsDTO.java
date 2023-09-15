package zeto.praktyki.Rent.RentDTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.annotation.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentListQueryParamsDTO {

    @Nullable
    private LocalDateTime from;
    @Nullable
    private LocalDateTime to;
    @Nullable
    private Double priceFrom;
    @Nullable
    private Double priceTo;
    @Nullable
    private Long carId;
    @Nullable
    private Long userId;
    @Nullable
    private Boolean returned;
    @Nullable
    private Boolean isLate;
}