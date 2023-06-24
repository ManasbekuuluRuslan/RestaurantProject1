package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    @Query("select new peaksoft.dto.response.RestaurantResponse" +
            "(r.id,r.name,r.location,r.restType) from Restaurant r")
    Page<RestaurantResponse> getAllRestaurants(Pageable pageable);
    Optional<RestaurantResponse> findRestaurantById (Long id);
    @Query("select count (u) from User u where u.restaurant.id = :id")
    int countUsers(Long id);

}
