package zeto.praktyki.Rent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import zeto.praktyki.Car.CarEntity;
import zeto.praktyki.Car.CarService;
import zeto.praktyki.Rent.RentDTO.AddRentDTO;
import zeto.praktyki.Rent.RentDTO.RentDTO;
import zeto.praktyki.User.Auth.JwtUtil;
import zeto.praktyki.User.Auth.JwtUtil.WhoCanAccess;

@RequestMapping("/api/rent")
@RestController
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RentController {

    @Autowired
    RentService rentService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping(path = "/add")
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
    public List<RentDTO> getRents() {
        return new ArrayList<RentDTO>(rentService.getRentsDTO());
    }
}
