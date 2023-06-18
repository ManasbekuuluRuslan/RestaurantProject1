package peaksoft.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
@Builder
@AllArgsConstructor
public class SimpleResponse {
    private String message;
    private HttpStatus httpStatus;
}