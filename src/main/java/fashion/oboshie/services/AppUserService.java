package fashion.oboshie.services;

import fashion.oboshie.models.AppUser;
import fashion.oboshie.models.AppUserPasswordResetRequest;
import fashion.oboshie.models.ConfirmationToken;
import fashion.oboshie.models.EmailSender;
import fashion.oboshie.repositories.AppUserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private final static String USER_NOT_FOUND_MESSAGE = "User with email %s not found";
    private final String UNLOCK_EMAIL_SUBJECT = "Oboshie Fasion💃: Unlock your Account";
    private final String UNLOCK_EMAIL_MESSAGE = "Your request has been received.\n" +
            "To ensure that you triggered this request, your account has been locked.\n" +
            "Please click on the below link to unlock your account:";
    private static final Logger LOGGER = LoggerFactory.getLogger(AppUserService.class);
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final EmailService emailService;
    private final PasswordValidatorService passwordValidatorService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, email)));
    }

    @Transactional
    public String signUpUser(AppUser appUser){
        boolean userExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
        if(userExists){
            //TODo: check if attributes are the same
            //ToDo: Resend mail if user has not been confirmed
                throw new IllegalStateException(String.format("Email '%s' already taken", appUser.getEmail()));
        }
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), appUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
         return token;
    }

    public int enableAppUser(String email){
        return  appUserRepository.enableAppUser(email);
    }

    public int disableAppUser(String email){
        return  appUserRepository.disableAppUser(email);
    }

    @Transactional
    public String unlockAppUser(String token){
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Account already unlocked");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        appUserRepository.unlockAppUser(confirmationToken.getAppUser().getEmail());
        confirmationTokenService.setConfirmedAt(token);
        return "Account unlocked";
    }

    @Transactional
    public boolean resetPassword(AppUserPasswordResetRequest appUserPasswordResetRequest){
        LOGGER.info(
                "===================================\n" +
                "PASSWORD RESET" +
                "===================================\n" +
                "Password reset initiated for ${}", appUserPasswordResetRequest);
        AppUser user = appUserRepository.findByEmail(appUserPasswordResetRequest.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, appUserPasswordResetRequest.getEmail())));

        boolean isValidPassword = passwordValidatorService.isPasswordSecured(appUserPasswordResetRequest.getNewPassword());
        if (!isValidPassword){
            LOGGER.error("Invalid Password: Password must be at least 8 characters long");
            throw new IllegalStateException("Password must be at least 8 characters long");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUserPasswordResetRequest.getNewPassword());
        user.setPassword(encodedPassword);
        user.setLocked(true);
        appUserRepository.save(user);
        LOGGER.info("Password has been reset and user account locked... Generating confirmation email now...");

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        String link = "http://localhost:8080/api/v1/authentication/unlock?token=" + token;
        emailSender.send(
                user.getEmail(),
                emailService.buildEmail(user.getFirstName(), link, UNLOCK_EMAIL_SUBJECT, UNLOCK_EMAIL_MESSAGE),
                UNLOCK_EMAIL_SUBJECT);
        LOGGER.error("Password reset confirmation email has been sent to ${}", user.getEmail());

        return true;
    }
}
