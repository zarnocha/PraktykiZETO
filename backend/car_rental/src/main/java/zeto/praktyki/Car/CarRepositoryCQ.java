package zeto.praktyki.Car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.util.Predicates;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import zeto.praktyki.Car.CarEnums.Drive;
import zeto.praktyki.Car.CarEnums.Gearbox;

@Repository
public class CarRepositoryCQ {

    @Autowired
    EntityManager em;

    @Transactional
    public List<CarEntity> findCarByBrandAndModelAndHorsePowerAndDriveAndGearbox(String brand, String model,
            Integer horsePowerFrom, Integer horsePowerTo, Gearbox gearbox, Drive drive) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CarEntity> cq = cb.createQuery(CarEntity.class);

        Root<CarEntity> car = cq.from(CarEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (brand != null) {
            predicates.add(cb.equal(car.get("brand"), brand));
        }

        if (model != null) {
            predicates.add(cb.equal(car.get("model"), model));
        }

        if (gearbox != null) {
            predicates.add(cb.equal(car.get("gearbox"), gearbox));
        }

        if (drive != null) {
            predicates.add(cb.equal(car.get("drive"), drive));
        }

        if (horsePowerFrom != null) {
            predicates.add(cb.greaterThanOrEqualTo(car.get("horsePower"), horsePowerFrom));
        }

        if (horsePowerTo != null) {
            predicates.add(cb.lessThanOrEqualTo(car.get("horsePower"), horsePowerTo));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<CarEntity> query = em.createQuery(cq);
        return query.getResultList();
    }
}
