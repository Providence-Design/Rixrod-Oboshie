package fashion.oboshie.controllers;

import fashion.oboshie.services.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/account")
@AllArgsConstructor
public class AppUserAccountController {
    private final AppUserService appUserService;

   @PostMapping(path = "change-password")
    public ResponseEntity<?> changeAppUserPassword(@RequestBody String newPassword){
       return ResponseEntity.ok("Password changed successfully");
   }
}
