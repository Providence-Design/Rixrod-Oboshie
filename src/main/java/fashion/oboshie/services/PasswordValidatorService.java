package fashion.oboshie.services;

import org.springframework.stereotype.Service;

@Service
public class PasswordValidatorService {
    public boolean isPasswordSecured(String password){
        return password.length() >= 8;
    }

}
