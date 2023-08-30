package zeto.praktyki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zeto.praktyki.model.Client;
import zeto.praktyki.rest.ClientRepo;

@RestController
public class ClientController {

    @Autowired
    ClientRepo repo;
    @PostMapping("/singUp")
    public void signUp(@RequestBody Client client){
        repo.save(client);
    }
}
