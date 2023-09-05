package zeto.praktyki.Car;

import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;
import zeto.praktyki.Car.CarEnums.Drive;
import zeto.praktyki.Car.CarEnums.Gearbox;
import zeto.praktyki.Rent.RentEntity;
import zeto.praktyki.User.UserEntity;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CarEntity.class)
public abstract class CarEntity_ {

	public static volatile SingularAttribute<CarEntity, Integer> horsePower;
	public static volatile SingularAttribute<CarEntity, String> description;
	public static volatile SingularAttribute<CarEntity, Integer> productionYear;
	public static volatile SetAttribute<CarEntity, RentEntity> rents;
	public static volatile SingularAttribute<CarEntity, Float> fuelConsumption;
	public static volatile SingularAttribute<CarEntity, Integer> seats;
	public static volatile SingularAttribute<CarEntity, Float> engineCapacity;
	public static volatile SingularAttribute<CarEntity, String> picture;
	public static volatile SingularAttribute<CarEntity, String> licensePlate;
	public static volatile SingularAttribute<CarEntity, UserEntity> added_by;
	public static volatile SingularAttribute<CarEntity, Double> price;
	public static volatile SingularAttribute<CarEntity, String> model;
	public static volatile SingularAttribute<CarEntity, Long> id;
	public static volatile SingularAttribute<CarEntity, Gearbox> gearbox;
	public static volatile SingularAttribute<CarEntity, Drive> drive;
	public static volatile SingularAttribute<CarEntity, String> brand;
	public static volatile SingularAttribute<CarEntity, Double> value;
	public static volatile SingularAttribute<CarEntity, Long> mileage;

	public static final String HORSE_POWER = "horsePower";
	public static final String DESCRIPTION = "description";
	public static final String PRODUCTION_YEAR = "productionYear";
	public static final String RENTS = "rents";
	public static final String FUEL_CONSUMPTION = "fuelConsumption";
	public static final String SEATS = "seats";
	public static final String ENGINE_CAPACITY = "engineCapacity";
	public static final String PICTURE = "picture";
	public static final String LICENSE_PLATE = "licensePlate";
	public static final String ADDED_BY = "added_by";
	public static final String PRICE = "price";
	public static final String MODEL = "model";
	public static final String ID = "id";
	public static final String GEARBOX = "gearbox";
	public static final String DRIVE = "drive";
	public static final String BRAND = "brand";
	public static final String VALUE = "value";
	public static final String MILEAGE = "mileage";

}

