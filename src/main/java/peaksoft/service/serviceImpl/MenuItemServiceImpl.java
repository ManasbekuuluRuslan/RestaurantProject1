package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.*;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.exeptions.IllegalArgumentExceptionn;
import peaksoft.exeptions.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.SubCategoryRepository;
import peaksoft.service.MenuItemService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    @Override
    public SimpleResponse saveMenu(Long resId, MenuItemRequest menuItemRequest) {
        Restaurant restaurant = restaurantRepository.findById(resId).orElseThrow(()->
                new NotFoundException("Restaurant with id: "+resId+" not found!"));
        MenuItem menuItem = new MenuItem();
        if(menuItemRequest.getPrice() < 0){
            throw new IllegalArgumentExceptionn("The price of food should not be a minus number");
        }

        menuItem.setName(menuItemRequest.getName());
        menuItem.setImage(menuItemRequest.getImage());
        menuItem.setPrice(menuItemRequest.getPrice());
        menuItem.setDescription(menuItemRequest.getDescription());
        menuItem.setVegetarian(menuItemRequest.isVegetarian());
        menuItem.setRestaurant(restaurant);
        menuItemRepository.save(menuItem);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Menu with name: %s is successfully saved",menuItemRequest.getName()))
                .build();
    }

    @Override
    public PaginationMenuRes getPagination(int page, int size,Boolean isVegetarian) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<MenuItemResponse> all = menuItemRepository.getAllMenu(pageable);
        List<MenuItemResponse> filter = all.stream().filter(menuItem -> isVegetarian == null || menuItem.isVegetarian() == isVegetarian).toList();
        return PaginationMenuRes.builder()
                .menuItemResponseList(filter)
                .currentPage(all.getNumber()+1)
                .pageSize(all.getTotalPages())
                .build();
    }

    @Override
    public MenuItemResponse getById(Long id) {
        return menuItemRepository.findMenuItemById(id).orElseThrow(()->
                new NotFoundException("Menu with id: "+id+" not found!"));
    }
    @Override
    public SimpleResponse updateMenu(Long id, MenuItemRequest menuItemRequest) {
       MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(()->
                new NotFoundException("Menu with id: "+id+" not found!"));
        menuItem.setImage(menuItemRequest.getImage());
        menuItem.setName(menuItemRequest.getName());
        menuItem.setPrice(menuItemRequest.getPrice());
        menuItem.setDescription(menuItemRequest.getDescription());
        menuItem.setVegetarian(menuItemRequest.isVegetarian());
        menuItemRepository.save(menuItem);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Menu with name: %s is successfully updated",menuItemRequest.getName()))
                .build();
    }

    @Override
    public SimpleResponse deleteMenu(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(
                ()->new NotFoundException("Menu with id: "+id+" doesn't exist!"));
        menuItemRepository.delete(menuItem);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK)
                .message(String.format("Menu with id: %s is successfully deleted",id))
                .build();
    }

}
