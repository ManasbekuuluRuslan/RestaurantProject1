package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.PaginationResponse;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.exeptions.ConflictException;
import peaksoft.exeptions.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.RestaurantService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequest.getName());
        restaurant.setLocation(restaurantRequest.getLocation());
        restaurant.setRestType(restaurantRequest.getRestType());
        restaurant.setNumberOfEmployees(restaurantRepository.countUsers(restaurant.getId()));
        restaurant.setService(restaurantRequest.getService());
        restaurantRepository.save(restaurant);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Restaurant with name: %s is successfully saved",restaurantRequest.getName()))
                .build();
    }

    @Override
    public PaginationResponse getPagination(int page,int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<RestaurantResponse> allRestaurants = restaurantRepository.getAllRestaurants(pageable);
        return PaginationResponse.builder()
                .responseList(allRestaurants.getContent())
                .currentPage(allRestaurants.getNumber()+1)
                .pageSize(allRestaurants.getTotalPages())
                .build();
    }

    @Override
    public RestaurantResponse getById(Long id) {
        return restaurantRepository.findRestaurantById(id).orElseThrow(()->
                new NotFoundException("Restaurant with id: "+id+" not found!"));
    }

    @Override
    public SimpleResponse updateRestaurant(Long id, RestaurantRequest restaurantRequest) {
      Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(()->
                new NotFoundException("Restaurant with id: "+id+" not found!"));
        restaurant.setLocation(restaurantRequest.getLocation());
        restaurant.setName(restaurantRequest.getName());
        restaurant.setRestType(restaurantRequest.getRestType());
        restaurant.setNumberOfEmployees(restaurantRequest.getNumberOfEmployees());
        restaurant.setService(restaurantRequest.getService());
        restaurantRepository.save(restaurant);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Restaurant with name: %s is successfully updated",
                        restaurantRequest.getName()))
                .build();
    }

    @Override
    public SimpleResponse deleteRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(
                ()->new NotFoundException("Restaurant with id: "+id+" doesn't exist!"));
        restaurantRepository.delete(restaurant);
        List<User> users = restaurant.getUserList();
        users.forEach(user -> user.setRestaurant(null));
        restaurantRepository.delete(restaurant);
        userRepository.saveAll(users);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Restaurant with id: %s is successfully deleted",id))
                .build();
    }
}
