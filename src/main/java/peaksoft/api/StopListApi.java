package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.*;
import peaksoft.service.StopListService;
@RestController
@RequestMapping("/stopList")
@RequiredArgsConstructor
public class StopListApi {
    private final StopListService stopListService;

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PostMapping("/{menuId}")
    public SimpleResponse saveStopList(@PathVariable Long menuId,@RequestBody StopListRequest s) {
        return stopListService.saveStopList(menuId, s);
    }
    @PermitAll
    @GetMapping
    public PaginationStopList getPagination(@RequestParam int page, @RequestParam int size) {
        return stopListService.getPagination(page, size);
    }

    @PermitAll
    @GetMapping("/{id}")
    public StopListResponse getById(@PathVariable Long id) {
        return stopListService.getById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/{id}")
    public SimpleResponse updateStopList(@PathVariable Long id, @RequestBody StopListRequest stopListRequest) {
        return stopListService.updateStopList(id, stopListRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteStopList(@PathVariable Long id) {
        return stopListService.deleteStopList(id);
    }
}
