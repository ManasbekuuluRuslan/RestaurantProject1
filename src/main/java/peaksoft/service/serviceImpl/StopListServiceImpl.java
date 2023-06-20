package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.*;
import peaksoft.entity.MenuItem;
import peaksoft.entity.StopList;
import peaksoft.exeptions.IllegalArgumentExceptionn;
import peaksoft.exeptions.NotFoundException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.StopListRepository;
import peaksoft.service.StopListService;

import java.time.ZonedDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class StopListServiceImpl implements StopListService {
    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;
    @Override
    public SimpleResponse saveStopList(Long menuId,StopListRequest stopListRequest) {
        MenuItem menuItem = menuItemRepository.findById(menuId).orElseThrow(()->
                new NotFoundException("Menu with id: "+menuId+" not found!"));
        ZonedDateTime date = stopListRequest.getDate();
        if(stopListRepository.existsStopListsByDate(date)){
                    throw new IllegalArgumentExceptionn("A StopList entry with the same date and MenuItem already exists " +
                            " One date and one food should be kept only once");
        }
        StopList stopList = new StopList();
        stopList.setReason(stopListRequest.getReason());
        stopList.setDate(date);
        stopList.setMenuItem(menuItem);
        menuItem.setStopList(stopList);
        stopListRepository.save(stopList);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK)
                .message(String.format("Menu with name: "+menuItem.getName()+
                        " added to StopList because "+stopListRequest.getReason()+
                        " and at this time |-> "+
                        stopListRequest.getDate())).build();
    }

    @Override
    public PaginationStopList getPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<StopListResponse> all = stopListRepository.getAllStopList(pageable);
        return PaginationStopList.builder()
                .stopListResponses(all.getContent())
                .currentPage(all.getNumber()+1)
                .pageSize(all.getTotalPages())
                .build();
    }

    @Override
    public StopListResponse getById(Long id) {
        return stopListRepository.findStopListById(id).orElseThrow(()->
                new NotFoundException("StopList with id: "+id+" not found!"));
    }

    @Override
    public SimpleResponse updateStopList(Long id, StopListRequest stopListRequest) {
       StopList stopList = stopListRepository.findById(id).orElseThrow(()->
                new NotFoundException("StopList with id: "+id+" not found!"));
        stopList.setReason(stopListRequest.getReason());
        stopList.setDate(ZonedDateTime.now());
        stopListRepository.save(stopList);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK)
                .message(String.format("StopList with reason: %s is  updated",
                        stopListRequest.getReason())).build();
    }

    @Override
    public SimpleResponse deleteStopList(Long id) {
        StopList stopList = stopListRepository.findById(id).orElseThrow(
                ()->new NotFoundException("StopList with id: "+id+" doesn't exist!"));
        stopListRepository.delete(stopList);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("StopList with id: %s is successfully deleted",id))
                .build();
    }
}
