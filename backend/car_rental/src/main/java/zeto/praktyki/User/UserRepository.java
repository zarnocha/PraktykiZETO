package zeto.praktyki.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RestController;
import zeto.praktyki.User.User;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {

}
