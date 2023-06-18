package peaksoft.service.serviceImpl;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.JwtService;
import peaksoft.dto.request.SignInRequest;
import peaksoft.dto.request.SignUpRequest;
import peaksoft.dto.response.AuthenticationResponse;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exeptions.BadCredentialException;
import peaksoft.exeptions.NotFoundException;
import peaksoft.repository.UserRepository;
import peaksoft.service.AuthenticationService;

import java.time.ZonedDateTime;


@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Override
    public AuthenticationResponse signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadCredentialException("Email already exists");
        }

        User user = User
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .dateOfBirth(ZonedDateTime.now())
                .experience(request.getExperience())
                .phoneNumber(request.getPhoneNumber())
                .build();
        userRepository.save(user);

        return AuthenticationResponse
                .builder()
                .token(jwtService.generateToken(user))
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public AuthenticationResponse signIn(SignInRequest request) {
        User user = userRepository.getUserByEmail(request.getEmail()).orElseThrow(
                () -> new NotFoundException("User with email: " + request.getEmail() + " not found!")
        );

        if (request.getPassword().isBlank()) {
            throw new BadCredentialException("Password is blank");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialException("Wrong password!");
        }

        return AuthenticationResponse
                .builder()
                .token(jwtService.generateToken(user))
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

//    @PostConstruct
//    public void initAdmin() {
//        User user = User.builder()
//                .firstName("Admin")
//                .lastName("Super")
//                .email("admin@gmail.com")
//                .password(passwordEncoder.encode("admin"))
//                .role(Role.ADMIN)
//                .dateOfBirth(ZonedDateTime.now())
//                .phoneNumber("0505555555")
//                .experience(9)
//                .build();
//        userRepository.save(user);
//    }
}