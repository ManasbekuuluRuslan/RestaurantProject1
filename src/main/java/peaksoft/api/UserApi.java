package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.*;
import peaksoft.entity.User;
import peaksoft.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse saveUser(@RequestBody UserRequest userRequest) {
        return userService.saveUser(userRequest);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{userId}/{restaurantId}")
    public SimpleResponse assignUserToCompany(@PathVariable Long userId, @PathVariable Long restaurantId) {
        return userService.assignUserToRestaurant(userId,restaurantId);
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/user/request")
    public SimpleResponse sendRequest(@RequestParam("restaurantId") Long restaurantId,@RequestBody User user) {
        return  userService.sendRequest(restaurantId,user);

    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/user/request")
    public SimpleResponse processRequest(@RequestParam("userName") String userName,
                                         @RequestParam("status") String requestStatus) {
        return  userService.processRequest(userName, requestStatus);
    }
}
