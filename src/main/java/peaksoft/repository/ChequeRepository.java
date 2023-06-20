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

import java.util.List;
import java.util.Optional;

public interface ChequeRepository extends JpaRepository<Cheque,Long> {

    Optional<ChequeResponse> findChequeById(Long id);

    @Query("select concat(u.firstName,' ' ,u.lastName) as fullname from User u where u.id=:id ")
    String userFullName(Long id);

    @Query("select new peaksoft.dto.response.MenuItemResponse" +
            "(m.id, m.name, m.image, m.price, m.description, m.isVegetarian)" +
            " from MenuItem m join m.chequeList mc where mc.user.id = :id")
    List<MenuItemResponse> getAllMenuItems(Long id);

    @Query("select new peaksoft.dto.response.MenuItemResponse" +
            "(m.id, m.name, m.image, m.price, m.description, m.isVegetarian)" +
            " from MenuItem m join m.chequeList mc where mc.user.id = :id")
    Page<ChequeResponse> getAllMenuItems(Long id, Pageable pageable);

    @Query("select avg(m.price) from MenuItem m join m.chequeList mc where mc.user.id = :id")
    Double averagePrice(Long id);


    @Query("select sum(m.price) from MenuItem m join m.chequeList mc where mc.user.id = :id")
    Double grandTotal(Long id);

    @Query("select sum (m.price)from MenuItem  m where m.restaurant.id=:restaurantId")
    Double getAllChecksSumsFromRestaurant(Long restaurantId);

}
