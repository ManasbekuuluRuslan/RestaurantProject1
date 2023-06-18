package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.entity.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {

    @Query("select new peaksoft.dto.response.MenuItemResponse" +
            "(m.id,m.name,m.image,m.price,m.description,m.isVegetarian) " +
            "from MenuItem m  order by m.price desc ")
    Page<MenuItemResponse> getAllMenu(Pageable pageable);

    Optional<MenuItemResponse> findMenuItemById(Long id);

}
