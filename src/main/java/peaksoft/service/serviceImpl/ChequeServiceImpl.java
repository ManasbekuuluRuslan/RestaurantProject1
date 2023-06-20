package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.*;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;
import peaksoft.entity.User;
import peaksoft.exeptions.NotFoundException;
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.ChequeService;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final UserRepository userRepository;


    @Override
    public SimpleResponse saveCheque(Long userId,ChequeRequest chequeRequest) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new NotFoundException("User with id: "+userId+" not found!"));
        Cheque cheque = new Cheque();
        cheque.setPriceAverage(chequeRequest.getPriceAverage());
        cheque.setCreatedAt(ZonedDateTime.now());
        cheque.setUser(user);
        if(user.getRole().name().equals("WAITER")){
            chequeRepository.save(cheque);
        }else {
            throw new NotFoundException("Waiter not found");
        }
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format(user.getFirstName() +" чекти чыгарды"))
                .build();
    }

    @Override
    public ChequeResponse getById(Long id) {
        return chequeRepository.findChequeById(id).orElseThrow(() ->
                new NotFoundException("Cheque with id: " + id + "  not found!"));
    }
    @Override
    public SimpleResponse updateCheque(Long id, ChequeRequest chequeRequest) {
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() ->
                    new NotFoundException("Cheque with id  " + id + "  not found!"));
        cheque.setPriceAverage(chequeRepository.averagePrice(cheque.getId()));
        cheque.setCreatedAt(ZonedDateTime.now());
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Cheque with id %s successfully updated!", cheque.getId()))
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
    public List<ChequeResponse> getInfoFromUser(Long id) {
        Double service = chequeRepository.grandTotal(id) * 12 / 100;
        List<ChequeResponse> chequeResponses = new ArrayList<>();
        ChequeResponse chequeResponse = new ChequeResponse();
        chequeResponse.setWaiterFullName(chequeRepository.userFullName(id));
        chequeResponse.setMenuItems(chequeRepository.getAllMenuItems(id));
        chequeResponse.setAveragePrice(chequeRepository.averagePrice(id));
        chequeResponse.setService(service);
        chequeResponse.setGrandTotal(chequeRepository.grandTotal(id));
        chequeResponses.add(chequeResponse);
        return chequeResponses;

    }

    @Override
    public Double getAllChecksSumFromRestaurantId(Long restaurantId) {
        return chequeRepository.getAllChecksSumsFromRestaurant(restaurantId);
    }
}
