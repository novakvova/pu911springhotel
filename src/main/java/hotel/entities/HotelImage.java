package hotel.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="tbl_hotel_images")
public class HotelImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name", length = 250)
    private String name;

    @ManyToOne
    private Hotel hotel;

    public HotelImage(String name) {
        this.name = name;
    }
}
