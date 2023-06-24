package peaksoft.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class SubCategoryResponse {
    private Long id;
    private String name;
   private String categoryName;

    public SubCategoryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SubCategoryResponse(Long id, String name, String categoryName) {
        this.id = id;
        this.name = name;
        this.categoryName = categoryName;
    }
}
