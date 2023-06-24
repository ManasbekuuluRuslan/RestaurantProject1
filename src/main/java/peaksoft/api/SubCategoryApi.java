package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.*;
import peaksoft.service.SubCategoryService;

@RestController
@RequestMapping("/subcategories")
@RequiredArgsConstructor
public class SubCategoryApi {
    private final SubCategoryService subCategoryService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{catId}")
    public SimpleResponse saveSubCategory(@PathVariable Long catId, @RequestBody SubCategoryRequest subCategoryRequest) {
        return subCategoryService.saveSubCategory(catId, subCategoryRequest);
    }
    @PermitAll
    @GetMapping
    public PaginationSubRes getPagination(@RequestParam int page, @RequestParam int size) {
        return subCategoryService.getPagination(page, size);
    }

    @PermitAll
    @GetMapping("/{id}")
    public SubCategoryResponse getById(@PathVariable Long id) {
        return subCategoryService.getById(id);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/{id}")
    public SimpleResponse updateSubCategory(@PathVariable Long id, @RequestBody SubCategoryRequest subCategoryRequest) {
        return subCategoryService.updateSubCategory(id, subCategoryRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteSubCategory(@PathVariable Long id) {
        return subCategoryService.deleteSubCategory(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/byGroup")
    public PaginationSubRes getSubCategoryByGroup(@RequestParam int page ,
                                                               @RequestParam int size){
        return subCategoryService.getAllSubCategoryByGroup(page, size);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/search")
    public PaginationSubRes search(@RequestParam String word,
                                                @RequestParam int page ,
                                                @RequestParam int size){
        return subCategoryService.searchByName(word, page, size);
    }
}
