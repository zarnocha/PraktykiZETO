package zeto.praktyki.rest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RestController;
import zeto.praktyki.model.Client;

@RepositoryRestResource
public interface ClientRepo extends JpaRepository<Client, Long> {

}
