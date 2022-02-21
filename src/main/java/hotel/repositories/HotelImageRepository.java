package hotel.repositories;

import hotel.entities.HotelImage;
import hotel.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelImageRepository extends JpaRepository<HotelImage, Integer> {
}
