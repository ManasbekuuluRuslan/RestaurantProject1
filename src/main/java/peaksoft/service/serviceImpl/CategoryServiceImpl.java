package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.*;
import peaksoft.entity.Category;
import peaksoft.exeptions.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.service.CategoryService;
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public SimpleResponse saveCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Category with name: %s is successfully saved",categoryRequest.getName()))
                .build();
    }

    @Override
    public PaginationCatRes getPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<CategoryResponse> all = categoryRepository.getAllCategories(pageable);
        return PaginationCatRes.builder()
                .categoryResponses(all.getContent())
                .currentPage(all.getNumber()+1)
                .pageSize(all.getTotalPages())
                .build();
    }

    @Override
    public CategoryResponse getById(Long id) {
        return categoryRepository.findCategoryById(id).orElseThrow(()->
                new NotFoundException("Category with id: "+id+" not found!"));
    }

    @Override
    public SimpleResponse updateCategory(Long id, CategoryRequest categoryRequest) {
      Category category = categoryRepository.findById(id).orElseThrow(()->
                new NotFoundException("Category with id: "+id+" not found!"));
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Category with name: %s is successfully updated",categoryRequest.getName()))
                .build();
    }

    @Override
    public SimpleResponse deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                ()->new NotFoundException("Category with id: "+id+" doesn't exist!"));
        categoryRepository.delete(category);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Category with id: %s is successfully deleted",id))
                .build();
    }
}
