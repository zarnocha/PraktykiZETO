package zeto.praktyki.Rent;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import zeto.praktyki.Rent.RentDTO.AddRentDTO;
import zeto.praktyki.Rent.RentDTO.RentDTO;
import zeto.praktyki.Rent.RentDTO.RentListQueryParamsDTO;
import zeto.praktyki.User.UserEntity;
import zeto.praktyki.User.Auth.JwtUtil;
import zeto.praktyki.User.Auth.JwtUtil.WhoCanAccess;
import zeto.praktyki.User.UserDTO.SimpleUserDTO;

@RequestMapping("/api/rent")
@RestController
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RentController {

    @Autowired
    RentService rentService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping(path = "add")
    public RentDTO addRent(@RequestBody AddRentDTO rent, @RequestHeader("Authorization") String bearerToken)
            throws Exception {
        jwtUtil.access(bearerToken, WhoCanAccess.USER);
        Long userId = jwtUtil.getUserIdFromToken(bearerToken);

        rent.setUserId(userId);

        RentDTO createdRent = rentService.addRent(rent);

        return createdRent;
    }

    @Transactional
    @GetMapping(path = "")
    public List<RentDTO> getRentsFiltered(RentListQueryParamsDTO rentListQueryParamsDTO,
            @RequestHeader("Authorization") String bearerToken) throws AuthException {
        jwtUtil.access(bearerToken, WhoCanAccess.ADMIN);
        return rentService.getRentListDTO(rentListQueryParamsDTO);
    }

    @GetMapping(path = "{id}")
    public RentDTO getSingleRent(@PathVariable long id, @RequestHeader("Authorization") String bearerToken)
            throws Exception {
        UserEntity user = jwtUtil.getUserFromToken(bearerToken);
        RentDTO wantedRent = new RentDTO(rentService.getRentById(id));

        if (!user.getIsAdmin() && !(wantedRent.getUser().equals(new SimpleUserDTO(user)))) {
            throw new AuthException("Nie masz dostępu do tego zasobu.");
        }

        return wantedRent;
    }

    @PostMapping(path = "take/{id}")
    public String markCarRentTake(@PathVariable Long id, @RequestHeader("Authorization") String bearerToken,
            @RequestBody(required = false) LocalDateTime startTime) throws Exception {

        startTime = (startTime == null) ? LocalDateTime.now() : startTime;
        return rentService.markCarRentTake(id, startTime);
    }

    @PostMapping(path = "return/{id}")
    public String markCarRentReturn(@PathVariable Long id, @RequestHeader("Authorization") String bearerToken,
            @RequestBody(required = false) LocalDateTime endTime) throws Exception {

        endTime = (endTime == null) ? LocalDateTime.now() : endTime;
        return rentService.markCarRentReturn(id, endTime);
    }
}
