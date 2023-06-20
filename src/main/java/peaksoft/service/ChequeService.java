package peaksoft.service;

import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.*;
import java.util.List;

public interface ChequeService {
    SimpleResponse saveCheque(Long userId,ChequeRequest chequeRequest);
//    PaginationCheque getPagination(int page, int size);
    ChequeResponse getById(Long id);
    SimpleResponse updateCheque(Long id, ChequeRequest chequeRequest);
    SimpleResponse deleteCheque(Long id);
    PaginationCheque getPagination(Long id,int page, int size);
    List<ChequeResponse> getInfoFromUser(Long id);
    Double getAllChecksSumFromRestaurantId(Long restaurantId);

}

