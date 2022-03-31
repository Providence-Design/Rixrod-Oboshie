package fashion.oboshie.services;

import fashion.oboshie.models.AppUser;
import fashion.oboshie.models.ConfirmationToken;
import fashion.oboshie.models.EmailSender;
import fashion.oboshie.models.RegistrationRequest;
import fashion.oboshie.models.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final String CONFIRM_EMAIL_SUBJECT = "Oboshie Fasion💃: Confirm your email";
    private final String CONFIRM_EMAIL_MESSAGE = "Thank you for registering. Please click on the below link to activate your account:";

    private final String BASE_URL = "http://localhost:8080/";
    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final PasswordValidatorService passwordValidatorService;
    private final EmailService emailService;

    @Transactional
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.
                test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("Email not valid");
        }

        boolean isValidPassword = passwordValidatorService.isPasswordSecured(request.getPassword());
        if (!isValidPassword) {
            throw new IllegalStateException("Password must be at least 8 characters long");
        }
        String token = appUserService.signUpUser(
            new AppUser(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                UserRole.USER
            )
        );

        String link = BASE_URL+"api/v1/authentication/confirm?token=" + token;
//        URI link = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/v1/authentication/confirm?token=" + token).toUriString());
        emailSender.send(
                request.getEmail(),
                emailService.buildEmail(request.getFirstName(), link, CONFIRM_EMAIL_SUBJECT, CONFIRM_EMAIL_MESSAGE),
                CONFIRM_EMAIL_SUBJECT);

        return link;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("Token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getAppUser().getEmail());
        return "Token confirmed";
    }

    @Transactional
    public String disableToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        confirmationTokenService.setExpiresAt(token);
        appUserService.disableAppUser(
                confirmationToken.getAppUser().getEmail());
        return "Token disabled";
    }
}
