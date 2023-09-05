package zeto.praktyki.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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