package ojt.management.schedulers;

import ojt.management.data.repositories.RefreshTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RefreshTokenScheduler {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenScheduler(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void deleteExpiredRefreshToken() {
        refreshTokenRepository.deleteAllByExpiryDateBefore(Instant.now());
    }
}
