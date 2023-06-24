package peaksoft.service;

import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.*;

public interface UserService {
    SimpleResponse saveUser(UserRequest userRequest);
    SimpleResponse saveUserToRes(Long resId,UserRequest userRequest);
    SimpleResponse assignUserToRestaurant(Long userId,Long restaurantId);
    SimpleResponse saveUserThroughAdmin(Long restaurantId, Long userId,String word);
    PaginationResUser getPagination(int page, int size);
    UserResponse getById(Long id);
    SimpleResponse updateUser(Long id, UserRequest userRequest);
    SimpleResponse deleteUser(Long id);
}
