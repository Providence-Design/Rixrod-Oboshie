package fashion.oboshie.repositories;

import fashion.oboshie.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
 @Repository
 @Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String Email);

     @Transactional
     @Modifying
     @Query("UPDATE AppUser a SET a.enabled = TRUE WHERE a.email = ?1")
     int enableAppUser(String email);

     @Transactional
     @Modifying
     @Query("UPDATE AppUser a SET a.enabled = FALSE WHERE a.email = ?1")
     int disableAppUser(String email);

     @Transactional
     @Modifying
     @Query("UPDATE AppUser a SET a.locked = TRUE WHERE a.id = ?1")
     int lockAppUser(long id);

     @Transactional
     @Modifying
     @Query("UPDATE AppUser a SET a.locked = FALSE WHERE a.email = ?1")
     int unlockAppUser(String email );


}
