package peaksoft.dto.response;

import lombok.*;
import peaksoft.enums.Role;

@Builder
public record AuthenticationResponse(String token, String email,Role role){
    public AuthenticationResponse {
    }
}
