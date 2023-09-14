package zeto.praktyki.Rent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import zeto.praktyki.Rent.RentDTO.RentListQueryParamsDTO;
import zeto.praktyki.User.UserEntity_;
import zeto.praktyki.User.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import zeto.praktyki.Car.CarEntity_;
import zeto.praktyki.Car.CarService;
import zeto.praktyki.Car.CarDTO.CarBrandModelDTO;
import zeto.praktyki.Car.CarDTO.CarFilterDTO;
import zeto.praktyki.Car.CarDTO.CarDTO;
import zeto.praktyki.Car.CarDTO.CarListQueryParamsDTO;
import zeto.praktyki.Rent.RentEntity;
import zeto.praktyki.Rent.RentEntity_;
import zeto.praktyki.Rent.RentDTO.RentDTO;

@Repository
public class RentRepositoryCQ {

    @Autowired
    EntityManager em;

    @Autowired
    CarService carService;

    @Autowired
    UserService userService;

    @Transactional
    public List<RentDTO> findRentByTimeAndPriceAndUserAndCarAndReturn(
            RentListQueryParamsDTO rentListQueryParamsDTO) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<RentDTO> cq = cb.createQuery(RentDTO.class);
        Root<RentEntity> rent = cq.from(RentEntity.class);

        List<Predicate> predicates = new ArrayList<>();
        cq.where(predicates.toArray(new Predicate[0]));

        cq.multiselect(
                rent.get(RentEntity_.id),
                rent.get(RentEntity_.startTime),
                rent.get(RentEntity_.endTime),
                rent.get(RentEntity_.actualStartTime),
                rent.get(RentEntity_.actualEndTime),
                rent.get(RentEntity_.price),
                rent.get(RentEntity_.car),
                rent.get(RentEntity_.user));

        if (rentListQueryParamsDTO.getCarId() != null) {
            predicates.add(cb.equal(rent.get(RentEntity_.car),
                    carService.getCarById(rentListQueryParamsDTO.getCarId())));
        }

        if (rentListQueryParamsDTO.getUserId() != null) {
            predicates.add(cb.equal(rent.get(RentEntity_.user),
                    userService.getUserById(rentListQueryParamsDTO.getUserId())));
        }

        if (rentListQueryParamsDTO.getPriceFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(rent.get(RentEntity_.price),
                    rentListQueryParamsDTO.getPriceFrom()));
        }

        if (rentListQueryParamsDTO.getPriceTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(rent.get(RentEntity_.price),
                    rentListQueryParamsDTO.getPriceTo()));
        }

        if (rentListQueryParamsDTO.getReturned() != null) {
            if (rentListQueryParamsDTO.getReturned()) {
                predicates.add(cb.isNotNull(rent.get(RentEntity_.actualEndTime)));
            } else {
                predicates.add(cb.isNotNull(rent.get(RentEntity_.actualStartTime)));
                predicates.add(cb.isNull(rent.get(RentEntity_.actualEndTime)));
            }
        }

        if (rentListQueryParamsDTO.getIsLate() != null) {
            // To < ACTUAL_END
            if (rentListQueryParamsDTO.getIsLate()) {
                // oddany
                Predicate A = cb.isNotNull(rent.get(RentEntity_.actualEndTime));
                Predicate B = cb.greaterThan(rent.get(RentEntity_.actualEndTime),
                        rent.get(RentEntity_.endTime));

                // nie oddany
                Predicate C = cb.isNull(rent.get(RentEntity_.actualEndTime));
                Predicate D = cb.greaterThan(rent.get(RentEntity_.endTime), LocalDateTime.now());

                Predicate AB = cb.and(A, B);
                Predicate CD = cb.and(C, D);

                predicates.add(cb.or(AB, CD));

            } else {
                Predicate A = cb.isNotNull(rent.get(RentEntity_.actualEndTime));
                Predicate B = cb.lessThanOrEqualTo(rent.get(RentEntity_.actualEndTime),
                        rent.get(RentEntity_.endTime));

                Predicate C = cb.isNull(rent.get(RentEntity_.actualEndTime));
                Predicate D = cb.lessThanOrEqualTo(rent.get(RentEntity_.endTime), LocalDateTime.now());

                Predicate AB = cb.and(A, B);
                Predicate CD = cb.and(C, D);

                predicates.add(cb.or(AB, CD));
            }
        }

        if (rentListQueryParamsDTO.getTo() != null && rentListQueryParamsDTO.getFrom() != null) {

            Predicate fromBetweenPredicate = cb.between(rent.get(RentEntity_.startTime),
                    rentListQueryParamsDTO.getFrom(),
                    rentListQueryParamsDTO.getTo());
            Predicate toBetweenPredicate = cb.between(rent.get(RentEntity_.endTime),
                    rentListQueryParamsDTO.getFrom(), rentListQueryParamsDTO.getTo());
            Predicate outerBeforePredicate = cb.lessThan(rent.get(RentEntity_.startTime),
                    rentListQueryParamsDTO.getFrom());
            Predicate outerAfterPredicate = cb.greaterThan(rent.get(RentEntity_.endTime),
                    rentListQueryParamsDTO.getTo());
            Predicate outerPredicate = cb.and(outerBeforePredicate, outerAfterPredicate);
            Predicate innerPredicate = cb.or(fromBetweenPredicate, toBetweenPredicate);

            predicates.add(cb.or(innerPredicate, outerPredicate));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        TypedQuery<RentDTO> query = em.createQuery(cq);
        return query.getResultList();
    }
}