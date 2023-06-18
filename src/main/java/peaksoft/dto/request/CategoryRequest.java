package peaksoft.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class CategoryRequest {
    private String name;

    public CategoryRequest(String name) {
        this.name = name;
    }
}
