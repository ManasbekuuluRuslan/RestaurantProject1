package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.PaginationResponse;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.RestaurantService;


@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantApi {
    private final RestaurantService restaurantService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse saveRestaurant(@RequestBody RestaurantRequest  restaurantRequest) {
        return restaurantService.saveRestaurant(restaurantRequest);
    }
    @PermitAll
    @GetMapping
    public PaginationResponse getPagination(@RequestParam int page, @RequestParam int size) {
        return restaurantService.getPagination(page, size);
    }

    @PermitAll
    @GetMapping("/{id}")
    public RestaurantResponse getById(@PathVariable Long id) {
        return restaurantService.getById(id);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/{id}")
    public SimpleResponse updateRestaurant(@PathVariable Long id, @RequestBody RestaurantRequest restaurantRequest) {
        return restaurantService.updateRestaurant(id, restaurantRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteRestaurant(@PathVariable Long id) {
        return restaurantService.deleteRestaurant(id);
    }
}