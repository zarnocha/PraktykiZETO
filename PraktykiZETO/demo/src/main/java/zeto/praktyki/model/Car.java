package zeto.praktyki.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="client")
public class Car {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String model;
    private String name;
    private float engine;
    private String consumption;
    private int people;
    private int capacity;
    private int vMax;
    private boolean available;
    private int licensePlate;
    private long kilometers;
    private String currentUser;
    private String previousUser;
    private long price;
    private String booking;
    private String description;
    private String lastRepair;
    private String lastRepairDesc;

}
