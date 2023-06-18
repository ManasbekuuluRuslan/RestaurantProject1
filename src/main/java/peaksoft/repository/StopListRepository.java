package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.StopListResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.StopList;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface StopListRepository extends JpaRepository<StopList,Long> {
@Query("select new peaksoft.dto.response.StopListResponse(s.id,s.reason,s.date) " +
        "from StopList s ")
    Page<StopListResponse> getAllStopList(Pageable pageable);


    Optional<StopListResponse> findStopListById(Long id);
    boolean existsStopListsByDate(ZonedDateTime date);
}
