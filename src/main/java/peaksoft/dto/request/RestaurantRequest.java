package peaksoft.dto.request;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class RestaurantRequest {
    private String name;
    private String location;
    private String restType;
    private Integer numberOfEmployees;
    private String service;

    public RestaurantRequest(String name, String location, String restType, Integer numberOfEmployees, String service) {
        this.name = name;
        this.location = location;
        this.restType = restType;
        this.numberOfEmployees = numberOfEmployees;
        this.service = service;
    }
}
