package zeto.praktyki.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zeto.praktyki.Car.CarEntity;
import zeto.praktyki.Car.CarService;

@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/signup")
    public void signUp(@RequestBody UserEntity user) {
        userRepository.save(user);
    }

    // @GetMapping("/{id}")
    // public void viewData(@PathVariable int id) {
    // userRepository.findById(id);
    // }

}