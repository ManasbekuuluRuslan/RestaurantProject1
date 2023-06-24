package peaksoft.service.serviceImpl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.JwtService;
import peaksoft.dto.request.SignInRequest;
import peaksoft.dto.request.SignUpRequest;
import peaksoft.dto.response.AuthenticationResponse;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exeptions.AllReadyExistException;
import peaksoft.exeptions.BadCredentialException;
import peaksoft.exeptions.NotFoundException;
import peaksoft.repository.UserRepository;
import peaksoft.service.AuthenticationService;

import java.time.LocalDate;



@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AuthenticationResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.email())) {
            throw new AllReadyExistException(String.format(
                    "User with email: %s already exists!", signUpRequest.email()));
        }
        User user = new User();
        user.setFirstName(signUpRequest.firstName());
        user.setLastName(signUpRequest.lastName());
        user.setEmail(signUpRequest.email());
        user.setDateOfBirth(signUpRequest.dateTime());
        user.setExperience(signUpRequest.experience());
        user.setPhoneNumber(signUpRequest.phoneNumber());
        user.setPassword(passwordEncoder.encode(signUpRequest.password()));
        user.setRole(signUpRequest.role());
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder().
                token(jwtToken).
                email(user.getEmail()).
                role(user.getRole()).
                build();
    }

    @Override
    public AuthenticationResponse signIn(SignInRequest signInRequest) {
        if (signInRequest.email().isBlank()) {
            throw new BadCredentialException("Email doesn't exist!");
        }
        User user = userRepository.getUserByEmail(signInRequest.email()).orElseThrow(() ->
             new NotFoundException("User with email: " + signInRequest.email() + " not found"));

        if (!passwordEncoder.matches(signInRequest.password(), user.getPassword())) {
            throw new BadCredentialException("Incorrect password!");
        }
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .email(user.getEmail())
                .role(user.getRole())
                .token(jwtToken)
                .build();
    }

    @PostConstruct
    private void initAdmin() {
        User admin = new User();
        admin.setFirstName("Ruslan");
        admin.setLastName("Kabylov");
        admin.setEmail("ruslan@gmail.com");
        admin.setDateOfBirth(LocalDate.of(2002, 4,12));
        admin.setPassword(passwordEncoder.encode("ruslan123"));
        admin.setPhoneNumber("+996505120402");
        admin.setRole(Role.ADMIN);
        if (!userRepository.existsByEmail("ruslan@gmail.com")) {
            userRepository.save(admin);
        }
    }
}