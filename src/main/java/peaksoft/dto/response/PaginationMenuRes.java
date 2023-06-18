package peaksoft.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class PaginationMenuRes {
    private List<MenuItemResponse> menuItemResponseList;
    private int pageSize;
    private int currentPage;
    public PaginationMenuRes(List<MenuItemResponse> menuItemResponseList, int pageSize, int currentPage) {
        this.menuItemResponseList = menuItemResponseList;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

}
