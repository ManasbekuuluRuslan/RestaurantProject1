package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.PaginationCheque;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.ChequeService;

import java.util.List;

@RestController
@RequestMapping("/cheques")
@RequiredArgsConstructor
public class ChequeApi {
    private final ChequeService chequeService;
    @GetMapping("/page/{id}")
    public PaginationCheque getPagination(@PathVariable Long id, @RequestParam int page, @RequestParam int size) {
        return chequeService.getPagination(id, page, size);
    }
    @PostMapping("/{userId}")
    public SimpleResponse saveCheque(@PathVariable Long userId, @RequestBody ChequeRequest chequeRequest) {
        return chequeService.saveCheque(userId, chequeRequest);
    }
    @GetMapping("/{id}")
    public ChequeResponse getChequeById(@PathVariable Long id) {
        return chequeService.getById(id);
    }
    @DeleteMapping("/{id}")
    public SimpleResponse deleteCheque(@PathVariable Long id) {
        return chequeService.deleteCheque(id);
    }
    @GetMapping("/all/{id}")
    List<ChequeResponse> getAllInfoFromUser(@PathVariable Long id) {
        return chequeService.getInfoFromUser(id);
    }
    @GetMapping("/sum/{restaurantId}")
    public Double getAllChecksSumsFromRestaurantId(@PathVariable Long restaurantId) {
        return chequeService.getAllChecksSumFromRestaurantId(restaurantId);

    }
}
