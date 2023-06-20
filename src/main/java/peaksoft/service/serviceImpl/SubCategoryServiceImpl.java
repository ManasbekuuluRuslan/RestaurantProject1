package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.*;
import peaksoft.entity.Category;
import peaksoft.entity.SubCategory;
import peaksoft.exeptions.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.SubCategoryRepository;
import peaksoft.service.SubCategoryService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public SimpleResponse saveSubCategory(Long categoryId, SubCategoryRequest subCategoryRequest) {
     Category category = categoryRepository.findById(categoryId).orElseThrow(()->
                new NotFoundException("Category with id: "+categoryId+" not found!"));
        SubCategory subCategory = new SubCategory();
        subCategory.setName(subCategoryRequest.getName());
        List<SubCategory> subcategories = new ArrayList<>();
        subcategories.add(subCategory);
        category.setSubCategoryList(subcategories);
        subCategory.setCategory(category);
        categoryRepository.save(category);
        subCategoryRepository.save(subCategory);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("SubCategory with name: %s is successfully saved",subCategoryRequest.getName()))
                .build();
    }

    @Override
    public PaginationSubRes getPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<SubCategoryResponse> all = subCategoryRepository.getAllSubCategories(pageable);
        return PaginationSubRes.builder()
                .subCategoryResponseList(all.getContent())
                .currentPage(all.getNumber()+1)
                .pageSize(all.getTotalPages())
                .build();
    }

    @Override
    public SubCategoryResponse getById(Long id) {
        return subCategoryRepository.findSubCategoryById(id).orElseThrow(()->
                new NotFoundException("SubCategory with id: "+id+" not found!"));
    }

    @Override
    public SimpleResponse updateSubCategory(Long id, SubCategoryRequest subCategoryRequest) {
       SubCategory subCategory = subCategoryRepository.findById(id).orElseThrow(()->
                new NotFoundException("SubCategory with id: "+id+" not found!"));
        subCategory.setName(subCategoryRequest.getName());
        subCategoryRepository.save(subCategory);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("SubCategory with name: %s is successfully updated",subCategoryRequest.getName()))
                .build();
    }

    @Override
    public SimpleResponse deleteSubCategory(Long id) {
        SubCategory subCategory = subCategoryRepository.findById(id).orElseThrow(
                ()->new NotFoundException("SubCategory with id: "+id+" doesn't exist!"));
        subCategoryRepository.delete(subCategory);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("SubCategory with id: %s is successfully deleted",id))
                .build();    }
}
