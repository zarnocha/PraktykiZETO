package zeto.praktyki.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletResponse;
import zeto.praktyki.Rent.RentRepositoryCQ;
import zeto.praktyki.Rent.RentDTO.RentListQueryParamsDTO;
import zeto.praktyki.User.Auth.JwtUtil;
import zeto.praktyki.User.Auth.JwtUtil.WhoCanAccess;
import zeto.praktyki.User.UserDTO.AdminRegisterDTO;
import zeto.praktyki.User.UserDTO.UserLoginDTO;
import zeto.praktyki.User.UserDTO.UserProfileDTO;
import zeto.praktyki.User.UserDTO.UserProfileWithRentsDTO;
import zeto.praktyki.User.UserDTO.UserRegisterDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    public RentRepositoryCQ rentRepositoryCQ;

    @PostMapping("/signup")
    public void signUp(@RequestBody UserRegisterDTO userRegisterDTO) {
        String plainPassword = userRegisterDTO.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(plainPassword);

        userRegisterDTO.setPassword(hashedPassword);
        UserEntity userEntity = new UserEntity(userRegisterDTO);
        userRepository.save(userEntity);
    }

    @PostMapping("/signup/admin")
    public void adminSignUp(@RequestBody AdminRegisterDTO adminRegisterDTO,
            @RequestHeader("Authorization") String bearerToken) throws AuthException {
        jwtUtil.access(bearerToken, WhoCanAccess.ADMIN);

        String plainPassword = adminRegisterDTO.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(plainPassword);

        adminRegisterDTO.setPassword(hashedPassword);
        UserEntity userEntity = new UserEntity(adminRegisterDTO);
        userRepository.save(userEntity);
    }

    @PostMapping("/login")
    public HashMap<String, String> login(@RequestBody UserLoginDTO userLoginDTO, HttpServletResponse response)
            throws AuthException {
        String loginFromRequest = userLoginDTO.getLogin();
        String givenPassword = userLoginDTO.getPassword();

        Optional<UserEntity> loggingUser = userRepository.findByLogin(loginFromRequest);
        if (loggingUser.isEmpty()) {
            throw new AuthException("Nie ma takiego użytkownika w bazie danych");
        }
        UserEntity foundUser = loggingUser.get();

        String storedHashedPassword = foundUser.getPassword().toString();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(givenPassword, storedHashedPassword)) {
            throw new AuthException("Niepoprawne dane logowania.");
        } else {
            Boolean isAdmin = foundUser.getIsAdmin();
            String firstName = foundUser.getFirstName();
            String lastName = foundUser.getLastName();
            Long id = foundUser.getId();

            Map<String, String> generatedToken = jwtUtil.generateToken(isAdmin, firstName, lastName, id);
            String token = generatedToken.get("token");
            String expirationDate = generatedToken.get("expires_at");

            response.addHeader("Bearer", token);

            return new HashMap<String, String>() {
                {
                    put("token", token);
                    put("expires_at", expirationDate);
                }
            };

        }
    }

    // @RequestHeader("Authorization") String bearerToken

    @GetMapping("/profile")
    public UserProfileWithRentsDTO viewData(RentListQueryParamsDTO rentListQueryParamsDTO,
            @RequestHeader("Authorization") String bearerToken) throws Exception {
        jwtUtil.access(bearerToken, WhoCanAccess.USER);
        UserEntity user = jwtUtil.getUserFromToken(bearerToken);
        Long userId = user.getId();
        rentListQueryParamsDTO.setUserId(userId);

        return new UserProfileWithRentsDTO(new UserProfileDTO(user),
                rentRepositoryCQ.findRentByTimeAndPriceAndUserAndCarAndReturn(rentListQueryParamsDTO));

    };

}