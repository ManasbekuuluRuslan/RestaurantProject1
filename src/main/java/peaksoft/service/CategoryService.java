package peaksoft.service;

import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.*;

public interface CategoryService {
    SimpleResponse saveCategory(CategoryRequest categoryRequest);
    PaginationCatRes getPagination(int page, int size);
    CategoryResponse getById(Long id);
    SimpleResponse updateCategory(Long id, CategoryRequest categoryRequest);
    SimpleResponse deleteCategory(Long id);
}
