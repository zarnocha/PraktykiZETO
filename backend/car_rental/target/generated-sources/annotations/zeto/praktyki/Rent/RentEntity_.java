package zeto.praktyki.Rent;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import zeto.praktyki.Car.CarEntity;
import zeto.praktyki.User.UserEntity;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RentEntity.class)
public abstract class RentEntity_ {

	public static volatile SingularAttribute<RentEntity, LocalDateTime> actualStartTime;
	public static volatile SingularAttribute<RentEntity, CarEntity> car;
	public static volatile SingularAttribute<RentEntity, Double> price;
	public static volatile SingularAttribute<RentEntity, LocalDateTime> actualEndTime;
	public static volatile SingularAttribute<RentEntity, LocalDateTime> startTime;
	public static volatile SingularAttribute<RentEntity, Long> id;
	public static volatile SingularAttribute<RentEntity, LocalDateTime> endTime;
	public static volatile SingularAttribute<RentEntity, UserEntity> user;

	public static final String ACTUAL_START_TIME = "actualStartTime";
	public static final String CAR = "car";
	public static final String PRICE = "price";
	public static final String ACTUAL_END_TIME = "actualEndTime";
	public static final String START_TIME = "startTime";
	public static final String ID = "id";
	public static final String END_TIME = "endTime";
	public static final String USER = "user";

}

