package peaksoft.service;

import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.*;

public interface MenuItemService {
    SimpleResponse saveMenu(Long resId,Long subId, MenuItemRequest menuItemRequest);
    PaginationMenuRes getPagination(int page, int size,Boolean isVegetarian);
    PaginationMenuRes getAllMenuItems(String ascOrDesc,int page, int size);
    MenuItemResponse getById(Long id);
    SimpleResponse updateMenu(Long id, MenuItemRequest menuItemRequest);
    SimpleResponse deleteMenu(Long id);
    PaginationMenuRes searchByName(String word,int page, int size);

}
