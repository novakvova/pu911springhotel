package hotel.entities;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name="tbl_hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name", length = 250)
    private String name;

    @Column(name="description",length = 4000)
    private String description;

}
