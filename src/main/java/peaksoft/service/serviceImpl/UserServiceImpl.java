package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.*;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exeptions.BadRequestException;
import peaksoft.exeptions.IllegalArgumentExceptionn;
import peaksoft.exeptions.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.UserService;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;

    private User getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userRepository.getUserByEmail(name).orElseThrow(() ->
                new NotFoundException("User with email: " + name + " us bit found!"));
        return user;
    }

    @Override
    public SimpleResponse saveUser(UserRequest userRequest) {
        User user = new User();
        int age = Period.between(userRequest.getDateOfBirth(), LocalDate.now()).getYears();

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
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRole(userRequest.getRole());
        user.setExperience(userRequest.getExperience());
        Restaurant restaurant = restaurantRepository.findById(1L).orElseThrow(() ->
                new NotFoundException("Restaurant not found !"));
        if(restaurant.getUserList().size() <=15){
            userRepository.save(user);
        }
        else throw new BadRequestException("We do not accept more than 15 employees, there is no vacation");
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("User with name: %s and Role "+userRequest.getRole()
                        +" is successfully saved",userRequest.getFirstName()))
                .build();
    }

    @Override
    public SimpleResponse saveUserToRes(Long resId, UserRequest userRequest) {
        Restaurant restaurant = restaurantRepository.findById(resId).orElseThrow(() ->
                new NotFoundException("Restaurant with id  " + resId + " not found !"));
        User user1 = new User();
        int age = Period.between(userRequest.getDateOfBirth(), LocalDate.now()).getYears();

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
                throw new BadRequestException("The age of the powar must be 2 years or more!");
            }
        }
        if (userRequest.getRole().name().equals("WAITER")){
            if(userRequest.getExperience() < 1 ){
                throw new BadRequestException("The age of the waiter must be 1 years or more!");
            }
        }
        if(userRequest.getPassword().length() < 4){
            throw new BadRequestException("Password should be more than 4 characters");
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
        user1.setFirstName(userRequest.getFirstName());
        user1.setLastName(userRequest.getLastName());
        user1.setDateOfBirth(userRequest.getDateOfBirth());
        user1.setEmail(userRequest.getEmail());
        user1.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user1.setPhoneNumber(userRequest.getPhoneNumber());
        user1.setRole(userRequest.getRole());
        user1.setExperience(userRequest.getExperience());
        if(restaurant.getUserList().size() <=15){
            userRepository.save(user1);
        }
        else throw new BadRequestException("We do not accept more than 15 employees, there is no vacation");

        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("User with name: %s and Role "+userRequest.getRole()
                        +" is successfully saved",userRequest.getFirstName()))
                .build();
    }

    @Override
    public SimpleResponse assignUserToRestaurant(Long userId, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() ->
                   new NotFoundException("Restaurant with id  " + restaurantId + " not found !"));
       User user = userRepository.findById(userId).orElseThrow(()->
                new NotFoundException("User with id: "+userId+" not found!"));

        if (restaurant.getNumberOfEmployees() <= 15) {
            List<User> users = new ArrayList<>();
            users.add(user);
            restaurant.setUserList(users);
            user.setRestaurant(restaurant);
            restaurantRepository.save(restaurant);
            userRepository.save(user);
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message(String.format("User with id: "+ userId+" successfully added to restaurant with id: "+restaurantId))
                    .build();
        } else {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.CONFLICT)
                    .message("Restaurant user count didn't more 15!")
                    .build();
        }
    }
    @Override
    public SimpleResponse saveUserThroughAdmin(Long restaurantId, Long userId, String word) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() ->
                new NotFoundException("Restaurant with id: " + restaurantId + " not found!"));
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User with id: " + userId + " not found!"));
        if (word.equalsIgnoreCase("accepted")) {
            restaurant.getUserList().add(user);
            restaurantRepository.save(restaurant);
            user.setRestaurant(restaurant);
            userRepository.save(user);
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Successfully accepted!")
                    .build();
        }
        if (word.equalsIgnoreCase("reject")) {
            userRepository.delete(user);
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Successfully rejected")
                    .build();
        }
        return null;
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
        User user1 = getAuthentication();
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("User with id: " + id + " not found!"));
        if (user1.getRole().name().equals("ADMIN")) {
            return UserResponse.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .dateOfBirth(user.getDateOfBirth())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .phoneNumber(user.getPhoneNumber())
                    .experience(user.getExperience())
                    .role(user.getRole())
                    .build();
        } else {
            if (user1.equals(user)) {
                return UserResponse.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .dateOfBirth(user.getDateOfBirth())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .phoneNumber(user.getPhoneNumber())
                        .experience(user.getExperience())
                        .role(user.getRole())
                        .build();
            } else throw new NotFoundException("you can't access this user: "+user.getId());
        }

    }

    @Override
    public SimpleResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(()->
                new NotFoundException("User with id: "+id+" not found!"));
        int age = Period.between(userRequest.getDateOfBirth(), LocalDate.now()).getYears();
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
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRole(userRequest.getRole());
        user.setExperience(userRequest.getExperience());
        userRepository.save(user);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("User with name: %s and Role "
                        +userRequest.getRole()+" is successfully updated",
                        userRequest.getFirstName())).build();
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
