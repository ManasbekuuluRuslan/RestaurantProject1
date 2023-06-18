package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.*;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.exeptions.IllegalArgumentExceptionn;
import peaksoft.exeptions.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.UserService;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    @Override
    public SimpleResponse saveUser(Long restaurantId, UserRequest userRequest) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(()->
                new NotFoundException("Restaurant with id: "+restaurantId+" not found!"));
        User user = new User();
        ZonedDateTime birthDate = userRequest.getDateOfBirth();
        ZonedDateTime currentDate = ZonedDateTime.now();
        long age = ChronoUnit.YEARS.between(birthDate, currentDate);
        if (userRequest.getRole().name().equals("CHEF")) {
            if (age < 25 || age > 45) {
                throw new IllegalArgumentExceptionn("Powar's age should be between 25 and 45 years");
            }
        } else if (userRequest.getRole().name().equals("WAITER")) {
            if (age < 18 || age > 30) {
                throw new IllegalArgumentExceptionn("The age of the waiter should be between 18 and 30 years");
            }
        }
        if(userRequest.getRole().name().equals("CHEF")){
            if(userRequest.getExperience() < 2){
                throw new IllegalArgumentExceptionn("The age of the powar must be 2 years or more!");
            }
        }
        if (userRequest.getRole().name().equals("WAITER")){
            if(userRequest.getExperience() < 1 ){
                throw new IllegalArgumentExceptionn("The age of the waiter must be 1 years or more!");
            }
        }
        if(userRequest.getPassword().length() < 4){
            throw new IllegalArgumentExceptionn("Password should be more than 4 characters");
        }
        if(userRequest.getFirstName().isEmpty() || userRequest.getLastName().isEmpty()){
            throw new IllegalArgumentExceptionn("All fields must be filled");
        }
        if(userRequest.getEmail().isEmpty() || userRequest.getRole().name().isEmpty()){
            throw new IllegalArgumentExceptionn("All fields must be filled");
        }
        if(userRequest.getExperience() == null || userRequest.getDateOfBirth() == null){
            throw new IllegalArgumentExceptionn("All fields must be filled");
        }
        if(userRequest.getPhoneNumber().isEmpty()){
            throw new IllegalArgumentExceptionn("All fields must be filled");
        }

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRole(userRequest.getRole());
        user.setExperience(userRequest.getExperience());
        user.setRestaurant(restaurant);
        userRepository.save(user);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("User with name: %s and Role "+userRequest.getRole()
                        +" is successfully saved",userRequest.getFirstName()))
                .build();
    }

    @Override
    public SimpleResponse sendRequest(Long restaurantId, User user) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant not found"));
        user.setRequestStatus("Pending");
        user.setRestaurant(restaurant);
        userRepository.save(user);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Request sending..."))
                .build();
    }

    @Override
    public SimpleResponse processRequest(Long userId, String requestStatus) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Employee not found"));

        if (requestStatus.equals("accept")) {
            user.setRequestStatus("Accepted");
        } else if (requestStatus.equals("reject")) {
            user.setRequestStatus("Rejected");
            user.setRestaurant(null);
        }
        userRepository.save(user);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Request processed!"))
                .build();
    }
    @Override
    public PaginationResUser getPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<UserResponse> allUsers = userRepository.getAllUsers(pageable);
        return PaginationResUser.builder()
                .userResponses(allUsers.getContent())
                .currentPage(allUsers.getNumber()+1)
                .pageSize(allUsers.getTotalPages())
                .build();
    }
    @Override
    public UserResponse getById(Long id) {
        return userRepository.findUserId(id).orElseThrow(()->
                new NotFoundException("User with id: "+id+" not found!"));
    }

    @Override
    public SimpleResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(()->
                new NotFoundException("User with id: "+id+" not found!"));
        ZonedDateTime birthDat = userRequest.getDateOfBirth();
        ZonedDateTime currentDate = ZonedDateTime.now();
        long age = ChronoUnit.YEARS.between(birthDat, currentDate);
        if (userRequest.getRole().name().equals("CHEF")) {
            if (age < 25 || age > 45) {
                throw new IllegalArgumentExceptionn("Powar's age should be between 25 and 45 years");
            }
        } else if (userRequest.getRole().name().equals("WAITER")) {
            if (age < 18 || age > 30) {
                throw new IllegalArgumentExceptionn("The age of the waiter should be between 18 and 30 years");
            }
        }
        if(userRequest.getRole().name().equals("CHEF")){
            if(userRequest.getExperience() < 2){
                throw new IllegalArgumentExceptionn("The age of the powar must be 2 years or more!");
            }
        }
        if (userRequest.getRole().name().equals("WAITER")){
            if(userRequest.getExperience() < 1 ){
                throw new IllegalArgumentExceptionn("The age of the waiter must be 1 years or more!");
            }
        }
        if(userRequest.getPassword().length() < 4){
            throw new IllegalArgumentExceptionn("Password should be more than 4 characters");
        }
        if(userRequest.getFirstName().isEmpty() || userRequest.getLastName().isEmpty()){
            throw new IllegalArgumentExceptionn("All fields must be filled");
        }
        if(userRequest.getEmail().isEmpty() || userRequest.getRole().name().isEmpty()){
            throw new IllegalArgumentExceptionn("All fields must be filled");
        }
        if(userRequest.getExperience() == null || userRequest.getDateOfBirth() == null){
            throw new IllegalArgumentExceptionn("All fields must be filled");
        }
        if(userRequest.getPhoneNumber().isEmpty()){
            throw new IllegalArgumentExceptionn("All fields must be filled");
        }
        user.setLastName(userRequest.getLastName());
        user.setFirstName(userRequest.getFirstName());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRole(userRequest.getRole());
        user.setExperience(userRequest.getExperience());
        userRepository.save(user);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("User with name: %s and Role "
                        +userRequest.getRole()+" is successfully updated",
                        userRequest.getFirstName()))
                .build();
    }

    @Override
    public SimpleResponse deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                ()->new NotFoundException("User with id: "+id+" doesn't exist!"));
        userRepository.delete(user);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("User with id: %s is successfully deleted",id))
                .build();
    }
}
