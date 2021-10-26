package ojt.management.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import ojt.management.configuration.security.services.UserDetailsImpl;
import ojt.management.constants.SecurityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private static final int JWT_EXPIRATION_MS = SecurityConstants.JWT_EXPIRATION_MS;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + JWT_EXPIRATION_MS))
                .signWith(key)
                .compact();
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + JWT_EXPIRATION_MS)).signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public ValidationStatus validateJwtToken(String authToken) {
        String message = "";
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return new ValidationStatus(true, "");
        } catch (SignatureException e) {
            message = String.format("Invalid JWT signature: %s", e.getMessage());
            logger.error(message);
        } catch (MalformedJwtException e) {
            message = String.format("Invalid JWT token: %s", e.getMessage());
            logger.error(message);
        } catch (ExpiredJwtException e) {
            message = String.format("JWT token is expired: %s", e.getMessage());
            logger.error(message);
        } catch (UnsupportedJwtException e) {
            message = String.format("JWT token is unsupported: %s", e.getMessage());
            logger.error(message);
        } catch (IllegalArgumentException e) {
            message = String.format("JWT claims string is empty: %s", e.getMessage());
            logger.error(message);
        }

        return new ValidationStatus(false, message);
    }

    public class ValidationStatus {
        private boolean valid;
        private String message;

        public ValidationStatus(boolean valid, String message) {
            this.valid = valid;
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }
    }
}
