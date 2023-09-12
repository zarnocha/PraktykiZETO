package zeto.praktyki.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity getUserById(long id) {
        if (userRepository.findById(id).isPresent()) {
            UserEntity foundUser = userRepository.findById(id).get();
            return foundUser;
        } else {
            throw new ResponseStatusException(HttpStatus.valueOf(403), "Nie ma takiego użytkownika w bazie danych.");
        }
    }
}
