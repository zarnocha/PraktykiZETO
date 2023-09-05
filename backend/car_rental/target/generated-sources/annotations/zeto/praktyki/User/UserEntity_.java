package zeto.praktyki.User;

import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import zeto.praktyki.Car.CarEntity;
import zeto.praktyki.Rent.RentEntity;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserEntity.class)
public abstract class UserEntity_ {

	public static volatile SingularAttribute<UserEntity, String> firstName;
	public static volatile SingularAttribute<UserEntity, String> lastName;
	public static volatile SetAttribute<UserEntity, CarEntity> cars;
	public static volatile SingularAttribute<UserEntity, String> password;
	public static volatile SingularAttribute<UserEntity, String> cvv;
	public static volatile SingularAttribute<UserEntity, LocalDate> creditCardExpDate;
	public static volatile SingularAttribute<UserEntity, Long> id;
	public static volatile SingularAttribute<UserEntity, Boolean> isAdmin;
	public static volatile SingularAttribute<UserEntity, String> login;
	public static volatile SetAttribute<UserEntity, RentEntity> rents;
	public static volatile SingularAttribute<UserEntity, String> creditCardNuber;

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String CARS = "cars";
	public static final String PASSWORD = "password";
	public static final String CVV = "cvv";
	public static final String CREDIT_CARD_EXP_DATE = "creditCardExpDate";
	public static final String ID = "id";
	public static final String IS_ADMIN = "isAdmin";
	public static final String LOGIN = "login";
	public static final String RENTS = "rents";
	public static final String CREDIT_CARD_NUBER = "creditCardNuber";

}

