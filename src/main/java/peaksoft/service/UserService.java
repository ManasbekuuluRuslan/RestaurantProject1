package peaksoft.service;

import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.*;
import peaksoft.entity.User;

public interface UserService {
    SimpleResponse saveUser(UserRequest userRequest);
    SimpleResponse assignUserToRestaurant(Long userId,Long restaurantId);

    SimpleResponse sendRequest(Long restaurantId,User user);
    SimpleResponse processRequest(String userName, String requestStatus);
    PaginationResUser getPagination(int page, int size);
    UserResponse getById(Long id);
    SimpleResponse updateUser(Long id, UserRequest userRequest);
    SimpleResponse deleteUser(Long id);
}
