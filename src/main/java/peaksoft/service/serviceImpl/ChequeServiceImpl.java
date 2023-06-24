package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.*;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.exeptions.NotFoundException;
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.ChequeService;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    private User getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userRepository.getUserByEmail(name).orElseThrow(() ->
                new NotFoundException("User with email: " + name + " not found!"));
        return user;
    }
    @Override
    public SimpleResponse saveCheque(Long userId,ChequeRequest chequeRequest) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User with id: " + userId + " not found!"));
        Cheque cheque = new Cheque();
        List<Cheque> cheques = new ArrayList<>();
        cheques.add(cheque);
        List<MenuItem> items = new ArrayList<>();
        for (Long l : chequeRequest.menuItemId()) {
            MenuItem menuItem = menuItemRepository.findById(l).orElseThrow(() ->
                    new NotFoundException(String.format("MenuItem with id: %s  not found", l)));
            menuItem.setChequeList(cheques);
            items.add(menuItem);
            int totalPrice = 0;
            for (MenuItem m : items) {
                totalPrice += m.getPrice();
            }
            cheque.setUser(user);
            cheque.setCreatedAt(LocalDate.now());
            cheque.setMenuItemList(items);
            cheque.setPriceAverage(totalPrice);
            if (user.getRole().name().equals("WAITER")) {
                chequeRepository.save(cheque);
            } else {
                throw new NotFoundException("Waiter not found");
            }
            return SimpleResponse
                    .builder()
                    .httpStatus(HttpStatus.OK)
                    .message(String.format(user.getFirstName() + " чекти чыгарды"))
                    .build();
        }
        return null;
    }

    @Override
    public ChequeResponse getById(Long id) {
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Cheque with id: %s not found", id)));
        Restaurant restaurant = restaurantRepository.findById(1L).orElseThrow(() -> new
                NotFoundException(String.format("Restaurant with id: %s not found", 1L)));
        return ChequeResponse.builder()
                .id(cheque.getId())
                .waiterFullName(cheque.getUser().getFirstName()+" "+cheque.getUser().getLastName())
                .menuItems(cheque.getMenuItemList())
                .service(restaurant.getService())
                .priceAverage(cheque.getPriceAverage())
                .grandTotal((int) (cheque.getPriceAverage()*0.15))
                .build();
    }
    @Override
    public SimpleResponse updateCheque(Long id, ChequeRequest chequeRequest) {
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Cheque with id: %s is not found", id)));
        List <MenuItem> items = new ArrayList<>();
        for (Long l: chequeRequest.menuItemId()) {
            Optional<MenuItem> menuItem = menuItemRepository.findById(l);
            MenuItem menuItem1 = new MenuItem();
            menuItem1.setId(menuItem.get().getId());
            menuItem1.setName(menuItem.get().getName());
            menuItem1.setImage(menuItem.get().getImage());
            menuItem1.setRestaurant(menuItem.get().getRestaurant());
            menuItem1.setVegetarian(menuItem.get().isVegetarian());
            menuItem1.setPrice(menuItem.get().getPrice());
            items.add(menuItem1);

        }
        int totalPrice = 0;
        for (MenuItem m:items) {
            totalPrice+=m.getPrice();
        }
        cheque.setPriceAverage(totalPrice);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully saved")
                .build();
    }

    @Override
    public SimpleResponse deleteCheque(Long id) {
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() ->
                    new NotFoundException("Cheque with id: " + id + "  not found!"));
        chequeRepository.delete(cheque);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Cheque with id: %s successfully deleted!", cheque.getId()))
                .build();
    }

    @Override
    public PaginationCheque getPagination(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<ChequeResponse>all = chequeRepository.getAllMenuItems(id,pageable);
        return PaginationCheque.builder()
                .chequeResponseList(all.getContent())
                .currentPage(all.getNumber()+1)
                .pageSize(all.getTotalPages())
                .build();
    }

    @Override
    public Double getAllChecksSumFromRestaurantId(Long restaurantId) {
        return chequeRepository.getAllChecksSumsFromRestaurant(restaurantId);
    }

    @Override
    public AverageSumResponse getAverageSum(LocalDate date) {
        int i = 0;
        List<Cheque> cheques = new ArrayList<>();
        List<User> all = userRepository.findAll();
        for (int j = 0; j < all.size(); j++) {
            cheques.addAll(all.get(j).getChequeList());
        }
        for (int k = 0; k < cheques.size(); k++) {
            if (cheques.get(k).getCreatedAt().equals(date)){
                i+=cheques.get(k).getPriceAverage();
            }
        }
        return AverageSumResponse.builder()
                .sum(i)
                .build();
    }

    @Override
    public AverageSumResponse getAverageSumOfWaiter(Long waiterId, LocalDate dateTime) {
        User user = userRepository.findById(waiterId).orElseThrow(() ->
                new NotFoundException(String.format("Waiter with id: %s not found", waiterId)));
        int i = chequeRepository.averageSumOfWaiter(user.getId(), dateTime);
        return AverageSumResponse.builder()
                .sum(i)
                .build();
    }
}
