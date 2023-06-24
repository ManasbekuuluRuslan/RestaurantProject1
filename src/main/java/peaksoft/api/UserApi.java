package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.*;
import peaksoft.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse saveUser(@RequestBody @Valid  UserRequest userRequest) {
        return userService.saveUser(userRequest);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/saveToRes/{resId}")
    public SimpleResponse saveUser(@PathVariable Long resId, @RequestBody UserRequest userRequest) {
        return userService.saveUserToRes(resId,userRequest);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{userId}/{restaurantId}")
    public SimpleResponse assignUserToCompany(@PathVariable Long userId, @PathVariable Long restaurantId) {
        return userService.assignUserToRestaurant(userId,restaurantId);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public  SimpleResponse saveUserThroughAdmin(@RequestParam Long restaurantId,@RequestParam Long userId, @RequestParam String word){
        return userService.saveUserThroughAdmin(restaurantId, userId, word);
    }
    @PermitAll
    @GetMapping
    public PaginationResUser getPagination(@RequestParam int page, @RequestParam int size) {
        return userService.getPagination(page, size);
    }
    @PermitAll
    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/{id}")
    public SimpleResponse updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return userService.updateUser(id, userRequest);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

}