package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.entity.SubCategory;
import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {
    @Query("select new peaksoft.dto.response.SubCategoryResponse(s.id,s.name) from SubCategory s")
    Page<SubCategoryResponse> getAllSubCategories(Pageable pageable);
    Optional<SubCategoryResponse> findSubCategoryById(Long id);
    @Query("select new peaksoft.dto.response.SubCategoryResponse(s.id,s.name,s.category.name) from SubCategory s group by s.id,s.name,s.category.name")
    Page<SubCategoryResponse> getAllSubCategoryByGroup(Pageable pageable);

    @Query("select new peaksoft.dto.response.SubCategoryResponse(s.id,s.name) from SubCategory s where s.name ilike concat(:word,'%') or " +
            "s.name ilike concat('%',:word,'%') ")
    Page<SubCategoryResponse> search(@Param("word") String word, Pageable pageable);}
