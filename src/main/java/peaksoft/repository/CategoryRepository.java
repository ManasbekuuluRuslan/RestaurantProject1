package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("select new peaksoft.dto.response.CategoryResponse(c.id,c.name) from Category c")
    Page<CategoryResponse> getAllCategories(Pageable pageable);

    Optional<CategoryResponse> findCategoryById(Long id);
    List<CategoryResponse> findByNameContainingIgnoreCase(String query);
    @Query("select new peaksoft.dto.response.CategoryResponse(c.id,c.name) from Category  c where c.name ilike  concat(:word,'%') " +
            "or c.name ilike concat('%',:word,'%') ")
    Page<CategoryResponse> search(@Param("word") String word, Pageable pageable);
}
