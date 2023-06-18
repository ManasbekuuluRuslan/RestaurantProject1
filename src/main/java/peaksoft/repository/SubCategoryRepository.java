package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.entity.SubCategory;

import java.util.List;
import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {
    @Query("select new peaksoft.dto.response.SubCategoryResponse(s.id,s.name) from SubCategory s")
    Page<SubCategoryResponse> getAllSubCategories(Pageable pageable);
    Optional<SubCategoryResponse> findSubCategoryById(Long id);
    List<SubCategoryResponse> findByNameContainingIgnoreCase(String query);
}
