package zeto.praktyki.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="client")
public class Client {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String login;
    private String name;
    private String lastName;
    private String pending;
    private String finished;
    private long creditCardNuber;
    private Date creditCardExpDate;
    private long cvv;

}
