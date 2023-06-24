package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.AverageSumResponse;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.ChequeService;

import java.time.LocalDate;

@RestController
@RequestMapping("/cheques")
@RequiredArgsConstructor

public class ChequeApi {
    private final ChequeService chequeService;
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @PostMapping
    public SimpleResponse saveCheque(@RequestParam Long userId, @RequestBody ChequeRequest chequeRequest){
        return chequeService.saveCheque(userId, chequeRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public SimpleResponse updateCheque(@PathVariable Long id,@RequestBody ChequeRequest chequeRequest){
        return chequeService.updateCheque(id, chequeRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @GetMapping("/{id}")
    public ChequeResponse getById(@PathVariable Long id){
        return chequeService.getById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteById(@PathVariable Long id){
        return chequeService.deleteCheque(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/averageSum")
    public AverageSumResponse getAverageSum(@RequestParam LocalDate date){
        return  chequeService.getAverageSum(date);
    }

    @PreAuthorize("hasAuthority('WAITER')")
    @GetMapping("/averageSumOfWaiter")
    public AverageSumResponse getAverageSumOfWaiter(@RequestParam Long id, @RequestParam LocalDate dateTime){
        return chequeService.getAverageSumOfWaiter(id, dateTime);
    }
}
