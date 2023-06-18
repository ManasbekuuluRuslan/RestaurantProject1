package peaksoft.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class SubCategoryRequest {
    private String name;

    public SubCategoryRequest(String name) {
        this.name = name;
    }
}
