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
public class PaginationResUser {
    private List<UserResponse> userResponses;
    private int pageSize;
    private int currentPage;

    public PaginationResUser(List<UserResponse> userResponses, int pageSize, int currentPage) {
        this.userResponses = userResponses;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }
}
