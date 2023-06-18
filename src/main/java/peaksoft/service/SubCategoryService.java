package peaksoft.service;

import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.*;

public interface SubCategoryService {
    SimpleResponse saveSubCategory(Long categoryId, SubCategoryRequest subCategoryRequest);
    PaginationSubRes getPagination(int page, int size);
    SubCategoryResponse getById(Long id);
    SimpleResponse updateSubCategory(Long id, SubCategoryRequest subCategoryRequest);
    SimpleResponse deleteSubCategory(Long id);
}
