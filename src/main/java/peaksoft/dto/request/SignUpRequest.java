package peaksoft.dto.request;

import lombok.AllArgsConstructor;
import peaksoft.enums.Role;
import peaksoft.validation.EmailValid;
import peaksoft.validation.PasswordValid;
import peaksoft.validation.PhoneNumberValid;

import java.time.LocalDate;

public record SignUpRequest (
        String firstName,
        String lastName,
        @EmailValid
        String email,
        @PasswordValid
        String password,
        LocalDate dateTime,
        Integer experience,
        @PhoneNumberValid
        String phoneNumber,
        Role role){

}