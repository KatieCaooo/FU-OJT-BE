package ojt.management.schedulers;

import ojt.management.data.repositories.RefreshTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Instant;

@Component
public class RefreshTokenScheduler {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenScheduler(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Scheduled(fixedRate = 1000 * 60 * 60)
    @Transactional
    public void deleteExpiredRefreshToken() {
        refreshTokenRepository.deleteAllByExpiryDateBefore(Instant.now());
    }
}
