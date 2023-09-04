package zeto.praktyki.Rent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import zeto.praktyki.Car.CarEntity;

@Repository
public class RentRepositoryCQ {

    @Autowired
    EntityManager em;

    @Transactional
    public List<RentEntity> findUnavailableRentsByDate(CarEntity car, LocalDateTime from,
            LocalDateTime to) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<RentEntity> cq = cb.createQuery(RentEntity.class);

        Root<RentEntity> rent = cq.from(RentEntity.class);

        Predicate carPredicate = cb.equal(rent.get("car"), car);

        Predicate fromBetweenPredicate = cb.between(rent.get("startTime"), from, to);
        Predicate toBetweenPredicate = cb.between(rent.get("endTime"), from, to);
        Predicate outerBeforePredicate = cb.lessThan(rent.get("startTime"), from);
        Predicate outerAfterPredicate = cb.greaterThan(rent.get("endTime"), to);

        Predicate outerPredicate = cb.and(outerBeforePredicate, outerAfterPredicate);
        Predicate innerPredicate = cb.or(fromBetweenPredicate, toBetweenPredicate);

        Predicate preFinalPredicate = cb.or(innerPredicate, outerPredicate);
        Predicate finalPredicate = cb.and(carPredicate, preFinalPredicate);

        cq.where(finalPredicate);
        // https://stackoverflow.com/questions/21383435/jpa-criteriabuilder-not-in-a-collection
        // criteriaQuery.where(finalPredicate);
        // List<Item> items = entityManager.createQuery(criteriaQuery).getResultList();

        TypedQuery<RentEntity> query = em.createQuery(cq);
        return query.getResultList();
    }

}