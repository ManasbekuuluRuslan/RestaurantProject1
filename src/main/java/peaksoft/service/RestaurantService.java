package peaksoft.service;

import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.PaginationResponse;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;

public interface RestaurantService {
    SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest);
    PaginationResponse getPagination(int page, int size);
    RestaurantResponse getById(Long id);
    SimpleResponse updateRestaurant(Long id, RestaurantRequest restaurantRequest);
    SimpleResponse deleteRestaurant(Long id);
}
