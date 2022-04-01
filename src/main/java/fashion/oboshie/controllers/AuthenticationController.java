package fashion.oboshie.controllers;

import fashion.oboshie.models.AppUser;
import fashion.oboshie.models.AppUserPasswordResetRequest;
import fashion.oboshie.models.AuthenticationRequest;
import fashion.oboshie.models.AuthenticationResponse;
import fashion.oboshie.models.RegistrationRequest;
import fashion.oboshie.services.AppUserService;
import fashion.oboshie.services.RegistrationService;
import fashion.oboshie.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(path = "api/v1/authentication")
@AllArgsConstructor
public class AuthenticationController {

    private final RegistrationService registrationService;
    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

//    @PostMapping
//    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) throws Exception {
//        try {
//            authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
//            );
//        }catch (BadCredentialsException e){
//            throw new Exception("Incorrect email or password");
//        }
//
//        final AppUser user = (AppUser) appUserService.loadUserByUsername(request.getEmail());
////        final String jwt = jwtUtil.generateTokens(user, request);
//        return ResponseEntity.ok().body(user);
//    }


    @PostMapping(path = "register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request){
        String link = registrationService.register(request);
        return ResponseEntity.created(URI.create(link)).body("Registration successful");
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @PostMapping(path = "reset-password")
    public ResponseEntity<?>  resetAppUserPassword(@RequestBody AppUserPasswordResetRequest appUserPasswordResetRequest){
        return (appUserService.resetPassword(appUserPasswordResetRequest))?
                ResponseEntity.status(HttpStatus.OK).body("Password reset successful") :
                ResponseEntity.internalServerError().body("Password reset failed");
    }

    @GetMapping(path = "unlock")
    public String unlock(@RequestParam("token") String token) {
        return appUserService.unlockAppUser(token);
    }

}
