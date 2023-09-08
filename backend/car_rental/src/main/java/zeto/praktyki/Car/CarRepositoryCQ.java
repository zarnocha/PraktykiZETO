package zeto.praktyki.Car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import zeto.praktyki.Car.CarDTO.CarBrandModelDTO;
import zeto.praktyki.Car.CarDTO.CarFilterDTO;
import zeto.praktyki.Car.CarDTO.CarListDTO;
import zeto.praktyki.Car.CarDTO.CarListQueryParamsDTO;
import zeto.praktyki.Rent.RentEntity;
import zeto.praktyki.Rent.RentEntity_;

@Repository
public class CarRepositoryCQ {

    @Autowired
    EntityManager em;

    @Transactional
    public List<CarListDTO> findCarByBrandAndModelAndHorsePowerAndDriveAndGearbox(
            CarListQueryParamsDTO carListQueryParams) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CarListDTO> cq = cb.createQuery(CarListDTO.class);
        Root<CarEntity> car = cq.from(CarEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        cq.where(predicates.toArray(new Predicate[0]));

        cq.multiselect(car.get(CarEntity_.id),
                car.get(CarEntity_.brand),
                car.get(CarEntity_.model),
                car.get(CarEntity_.productionYear),
                car.get(CarEntity_.fuelConsumption),
                car.get(CarEntity_.engineCapacity),
                car.get(CarEntity_.drive),
                car.get(CarEntity_.seats),
                car.get(CarEntity_.gearbox),
                car.get(CarEntity_.description),
                car.get(CarEntity_.picture),
                car.get(CarEntity_.value));

        Join<CarEntity, RentEntity> rentsJoin = car.join(CarEntity_.rents,
                JoinType.LEFT);

        if (carListQueryParams.getBrand() != null) {
            predicates.add(cb.equal(car.get(CarEntity_.brand),
                    carListQueryParams.getBrand()));
        }

        if (carListQueryParams.getModel() != null) {
            predicates.add(cb.equal(car.get(CarEntity_.model),
                    carListQueryParams.getModel()));
        }

        if (carListQueryParams.getGearbox() != null) {
            predicates.add(cb.equal(car.get(CarEntity_.gearbox),
                    carListQueryParams.getGearbox()));
        }

        if (carListQueryParams.getDrive() != null) {
            predicates.add(cb.equal(car.get(CarEntity_.drive),
                    carListQueryParams.getDrive()));
        }

        if (carListQueryParams.getHorsePowerFrom() != null) {
            predicates.add(
                    cb.greaterThanOrEqualTo(car.get(CarEntity_.horsePower),
                            carListQueryParams.getHorsePowerFrom()));
        }

        if (carListQueryParams.getHorsePowerTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(car.get(CarEntity_.horsePower),
                    carListQueryParams.getHorsePowerTo()));
        }

        Predicate doesntHaveRentsPredicate = cb.isEmpty(car.get(CarEntity_.rents));

        if (carListQueryParams.getTo() != null && carListQueryParams.getFrom() != null) {

            Predicate fromBetweenPredicate = cb.between(rentsJoin.get(RentEntity_.startTime),
                    carListQueryParams.getFrom(),
                    carListQueryParams.getTo());
            Predicate toBetweenPredicate = cb.between(rentsJoin.get(RentEntity_.endTime),
                    carListQueryParams.getFrom(), carListQueryParams.getTo());
            Predicate outerBeforePredicate = cb.lessThan(rentsJoin.get(RentEntity_.startTime),
                    carListQueryParams.getFrom());
            Predicate outerAfterPredicate = cb.greaterThan(rentsJoin.get(RentEntity_.endTime),
                    carListQueryParams.getTo());
            Predicate outerPredicate = cb.and(outerBeforePredicate, outerAfterPredicate);
            Predicate innerPredicate = cb.or(fromBetweenPredicate, toBetweenPredicate);

            Predicate preFinalPredicate = cb.or(innerPredicate, outerPredicate).not();
            Predicate finalPredicate = cb.or(preFinalPredicate,
                    doesntHaveRentsPredicate);

            if (carListQueryParams.getAvailable() != null) {
                predicates.add(finalPredicate);
            } else {
                predicates.add(finalPredicate.not());
            }
        }

        Predicate all = cb.and(predicates.toArray(new Predicate[0]));

        cq.where(all);

        TypedQuery<CarListDTO> query = em.createQuery(cq);
        return query.getResultList();
    }

    @Transactional
    public List<CarBrandModelDTO> getCarBrandModels() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = criteriaBuilder.createQuery(Object[].class);
        Root<CarEntity> carRoot = query.from(CarEntity.class);

        query.multiselect(carRoot.get("brand"),
                criteriaBuilder.function("array_agg", String.class, carRoot.get("model")))
                .groupBy(carRoot.get("brand"));

        List<Object[]> results = em.createQuery(query).getResultList();
        List<CarBrandModelDTO> carBrandModelDTOs = new ArrayList<>();

        for (Object[] result : results) {
            String brand = (String) result[0];
            String modelsConcatenated = (String) result[1];
            Set<String> models = new HashSet<>();
            if (modelsConcatenated != null) {
                String[] modelArray = modelsConcatenated.replaceAll("\"", "").replaceAll("[{}]", "")
                        .split(",");
                models.addAll(new HashSet<>(Arrays.asList(modelArray)));

            }

            CarBrandModelDTO carBrandModelDTO = new CarBrandModelDTO(brand, models);
            carBrandModelDTOs.add(carBrandModelDTO);
        }
        return carBrandModelDTOs;
    }

    @Transactional
    public CarFilterDTO getCarFilterDTO() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<CarFilterDTO> query = criteriaBuilder.createQuery(CarFilterDTO.class);

        Root<CarEntity> car = query.from(CarEntity.class);

        Expression<Integer> maxExpression = criteriaBuilder.max(car.get(CarEntity_.horsePower));
        Expression<Integer> minExpression = criteriaBuilder.min(car.get(CarEntity_.horsePower));

        query.select(criteriaBuilder.construct(CarFilterDTO.class, minExpression, maxExpression));

        CarFilterDTO results = em.createQuery(query).getSingleResult();

        return new CarFilterDTO(results.getMinHorsepower(), results.getMaxHorsepower(), getCarBrandModels());
    }
}