package zeto.praktyki.Rent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;

@Repository
public class RentRepositoryCQ {

    @Autowired
    EntityManager em;
}