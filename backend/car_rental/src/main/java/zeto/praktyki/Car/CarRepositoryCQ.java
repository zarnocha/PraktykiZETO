package zeto.praktyki.Car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import zeto.praktyki.Car.CarDTO.CarListQueryParamsDTO;
import zeto.praktyki.Rent.RentEntity;
import zeto.praktyki.Rent.RentEntity_;

@Repository
public class CarRepositoryCQ {

    @Autowired
    EntityManager em;

    @Transactional
    public List<CarEntity> findCarByBrandAndModelAndHorsePowerAndDriveAndGearbox(
            CarListQueryParamsDTO carListQueryParams) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CarEntity> cq = cb.createQuery(CarEntity.class);

        Root<CarEntity> car = cq.from(CarEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (carListQueryParams.getBrand() != null) {
            predicates.add(cb.equal(car.get(CarEntity_.brand), carListQueryParams.getBrand()));
        }

        if (carListQueryParams.getModel() != null) {
            predicates.add(cb.equal(car.get(CarEntity_.model), carListQueryParams.getModel()));
        }

        if (carListQueryParams.getGearbox() != null) {
            predicates.add(cb.equal(car.get(CarEntity_.gearbox), carListQueryParams.getGearbox()));
        }

        if (carListQueryParams.getDrive() != null) {
            predicates.add(cb.equal(car.get(CarEntity_.drive), carListQueryParams.getDrive()));
        }

        if (carListQueryParams.getHorsePowerFrom() != null) {
            predicates.add(
                    cb.greaterThanOrEqualTo(car.get(CarEntity_.horsePower), carListQueryParams.getHorsePowerFrom()));
        }

        if (carListQueryParams.getHorsePowerTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(car.get(CarEntity_.horsePower), carListQueryParams.getHorsePowerTo()));
        }

        Join<CarEntity, RentEntity> rentsJoin = car.join(CarEntity_.rents, JoinType.LEFT);

        Predicate doesntHaveRentsPredicate = cb.isEmpty(car.get(CarEntity_.rents));

        Predicate fromBetweenPredicate = cb.between(rentsJoin.get(RentEntity_.startTime), carListQueryParams.getFrom(),
                carListQueryParams.getTo());
        Predicate toBetweenPredicate = cb.between(rentsJoin.get(RentEntity_.endTime),
                carListQueryParams.getFrom(), carListQueryParams.getTo());
        Predicate outerBeforePredicate = cb.lessThan(rentsJoin.get(RentEntity_.startTime),
                carListQueryParams.getFrom());
        Predicate outerAfterPredicate = cb.greaterThan(rentsJoin.get(RentEntity_.endTime), carListQueryParams.getTo());

        Predicate outerPredicate = cb.and(outerBeforePredicate, outerAfterPredicate);
        Predicate innerPredicate = cb.or(fromBetweenPredicate, toBetweenPredicate);

        Predicate preFinalPredicate = cb.or(innerPredicate, outerPredicate).not();
        Predicate finalPredicate = cb.or(preFinalPredicate, doesntHaveRentsPredicate);

        if (carListQueryParams.getAvailable()) {
            predicates.add(finalPredicate);
        } else {
            predicates.add(finalPredicate.not());
        }

        Predicate all = cb.and(predicates.toArray(new Predicate[0]));

        cq.where(all);

        TypedQuery<CarEntity> query = em.createQuery(cq);
        return query.getResultList();
    }
}
// TODO: aktywny nieaktywny, DTO, budowanie maven, metamodel CarEntity_
