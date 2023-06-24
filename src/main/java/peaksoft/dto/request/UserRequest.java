package peaksoft.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import peaksoft.enums.Role;

import java.time.LocalDate;
import java.time.ZonedDateTime;
@Data
@NoArgsConstructor
@Builder
public class UserRequest {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String password;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Integer experience;

    public UserRequest(String firstName, String lastName,
                       LocalDate dateOfBirth, String email,
                       String password, String phoneNumber,
                       Role role, Integer experience) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.experience = experience;
    }
}
