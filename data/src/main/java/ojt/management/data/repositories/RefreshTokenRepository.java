package ojt.management.data.repositories;

import ojt.management.data.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    @Transactional
    void deleteAllByExpiryDateBefore(Instant date);

//    @Modifying
//    int deleteByAccount(Account account);
}
