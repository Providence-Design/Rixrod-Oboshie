package fashion.oboshie.controllers;

import fashion.oboshie.services.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/account")
@AllArgsConstructor
public class AppUserAccountController {
    private final AppUserService appUserService;

   @PutMapping(path = "lock")
    public String  appUserLock(@RequestBody long id){
       return appUserService.lockAppUser(id);
   }

   @PutMapping(path = "unlock")
    public String appUserUnlock(@RequestBody long id){
       return appUserService.unlockAppUser(id);
   }
}
