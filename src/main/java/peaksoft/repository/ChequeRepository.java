package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ChequeRepository extends JpaRepository<Cheque,Long> {

    Optional<ChequeResponse> findChequeById(Long id);
    @Query("select new peaksoft.dto.response.MenuItemResponse" +
            "(m.id, m.name, m.image, m.price, m.description, m.isVegetarian)" +
            " from MenuItem m join m.chequeList mc where mc.user.id = :id")
    List<MenuItemResponse> getAllMenuItems(Long id);

    @Query("select new peaksoft.dto.response.MenuItemResponse" +
            "(m.id, m.name, m.image, m.price, m.description, m.isVegetarian)" +
            " from MenuItem m join m.chequeList mc where mc.user.id = :id")
    Page<ChequeResponse> getAllMenuItems(Long id, Pageable pageable);

    @Query("select sum(c.priceAverage) from Cheque c join c.user u join u.chequeList ch where u.id=:id and ch.createdAt=:date")
    int averageSumOfWaiter(@Param("id") Long id,@Param("date") LocalDate date);

    @Query("select sum (m.price)from MenuItem  m where m.restaurant.id=:restaurantId")
    Double getAllChecksSumsFromRestaurant(Long restaurantId);

}
