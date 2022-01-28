package hotel.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="tbl_region")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 200, nullable = false)
    private String name;
}
