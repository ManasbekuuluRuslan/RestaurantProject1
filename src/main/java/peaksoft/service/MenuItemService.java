package peaksoft.service;

import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.*;

import javax.naming.directory.SearchResult;
import java.util.List;

public interface MenuItemService {
    SimpleResponse saveMenu(Long resId, MenuItemRequest menuItemRequest);
    PaginationMenuRes getPagination(int page, int size,Boolean isVegetarian);
    MenuItemResponse getById(Long id);
    SimpleResponse updateMenu(Long id, MenuItemRequest menuItemRequest);
    SimpleResponse deleteMenu(Long id);
}
