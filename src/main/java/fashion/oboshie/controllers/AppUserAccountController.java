package fashion.oboshie.controllers;

import fashion.oboshie.models.AppUserPasswordResetRequest;
import fashion.oboshie.services.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/account")
@AllArgsConstructor
public class AppUserAccountController {
    private final AppUserService appUserService;

   @PostMapping(path = "reset-password")
    public String  resetAppUserPassword(@RequestBody AppUserPasswordResetRequest appUserPasswordResetRequest){
       return appUserService.resetPassword(appUserPasswordResetRequest);
   }

    @GetMapping(path = "unlock")
    public String confirm(@RequestParam("token") String token) {
        return appUserService.unlockAppUser(token);
    }
}
