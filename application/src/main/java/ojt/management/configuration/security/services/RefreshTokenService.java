package ojt.management.configuration.security.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import ojt.management.common.exceptions.TokenRefreshException;
import ojt.management.data.entities.RefreshToken;
import ojt.management.data.repositories.AccountRepository;
import ojt.management.data.repositories.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static ojt.management.constants.SecurityConstants.JWT_REFRESH_EXPIRATION_MS;


@Service
public class RefreshTokenService {
    private int refreshTokenDurationMs = JWT_REFRESH_EXPIRATION_MS;

    private final RefreshTokenRepository refreshTokenRepository;

    private final AccountRepository accountRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, AccountRepository accountRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.accountRepository = accountRepository;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long accountId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setAccount(accountRepository.findById(accountId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

//    @Transactional
//    public int deleteByUserId(Long accountId) {
//        return refreshTokenRepository.deleteByAccount(accountRepository.findById(accountId).get());
//    }
}