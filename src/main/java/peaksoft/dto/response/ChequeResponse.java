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
public class ChequeResponse {
    private Long id;
   private String waiterFullName;
   private List<MenuItemResponse> menuItems;
   private Double averagePrice;
   private Double service;
   private Double grandTotal;

    public ChequeResponse(Long id,String waiterFullName, List<MenuItemResponse> menuItems, Double averagePrice, Double service, Double grandTotal) {
        this.waiterFullName = waiterFullName;
        this.menuItems = menuItems;
        this.averagePrice = averagePrice;
        this.service = service;
        this.grandTotal = grandTotal;
    }
}
