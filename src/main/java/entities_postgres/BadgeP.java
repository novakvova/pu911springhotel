package entities_postgres;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="badges")
public class BadgeP {
    @Id
    @Column(name="id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="name", length = 40, nullable = false)
    private String name;
    @Column(name = "user_d")
    private int userId;
    @Column(name="date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
