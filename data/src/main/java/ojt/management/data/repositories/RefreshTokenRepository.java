package ojt.management.data.repositories;

import ojt.management.data.entities.Account;
import ojt.management.data.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteAllByExpiryDateBefore(Instant date);

//    @Modifying
//    int deleteByAccount(Account account);
}
