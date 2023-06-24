package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.*;
import peaksoft.service.CategoryService;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryApi {
    private final CategoryService categoryService;
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping
    public PaginationCatRes getAllCategories(@RequestParam int page,@RequestParam int size){
        return categoryService.getPagination(page, size);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse saveCategory(@RequestBody CategoryRequest categoryRequest){
        return categoryService.saveCategory(categoryRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public SimpleResponse updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest){
        return categoryService.updateCategory(id,categoryRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id){
        return categoryService.getById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteCategory(@PathVariable Long id){
        return categoryService.deleteCategory(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/search")
    public PaginationCatRes search(@RequestParam String word,@RequestParam int page,@RequestParam int size){
        return categoryService.searchCategoryByName(word, page, size);
    }
}
