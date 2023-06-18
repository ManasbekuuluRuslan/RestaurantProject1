package peaksoft.service;

import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.*;

public interface StopListService {
    SimpleResponse saveStopList(Long menuId,StopListRequest stopListRequest);
    PaginationStopList getPagination(int page, int size);
    StopListResponse getById(Long id);
    SimpleResponse updateStopList(Long id, StopListRequest stopListRequest);
    SimpleResponse deleteStopList(Long id);
}
