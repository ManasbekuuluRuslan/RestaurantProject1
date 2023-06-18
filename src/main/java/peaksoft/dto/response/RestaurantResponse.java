package peaksoft.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class RestaurantResponse {
    private Long id;
    private String name;
    private String location;
    private String restType;

    public RestaurantResponse(Long id, String name, String location, String restType) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.restType = restType;
    }
}
