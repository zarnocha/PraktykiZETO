package zeto.praktyki.User.Auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.security.auth.message.AuthException;
import zeto.praktyki.User.UserEntity;
import zeto.praktyki.User.UserRepository;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class JwtUtil {

    public static enum WhoCanAccess {
        EVERYONE,
        USER,
        ADMIN
    }

    private static final String SECRET_KEY = "smittenturbanspoilerpersuadedhedgesquealingcelibatefergthgrtfghjrfgedfhy";

    @Autowired
    private final UserRepository userRepository;

    // final List<String> USER_ROLES = Arrays.asList("wlasciciel", "kierownik",
    // "pracownik");

    public JwtUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Map<String, String> generateToken(Boolean isAdmin, String firstName, String lastName, Long id) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("isAdmin", isAdmin);
        claims.put("firstName", firstName);
        claims.put("lastName", lastName);
        claims.put("sub", id);

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + 1 * 60 * 60 * 1000L); // Token expires in 1 hour

        return new HashMap<String, String>() {
            {
                put("token", Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(expirationDate)
                        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                        .compact());
                put("expires_at", expirationDate.toInstant().toString());
            }
        };
    }

    public Boolean authorize(String token, WhoCanAccess whoCanAccess) {
        try {
            if (token != null && token.startsWith("Bearer")) {
                token = token.substring(7, token.length());
            } else
                throw new Exception("Nieprawidłowy token.");

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Long userIdFromJwt = Long.valueOf(claims.getSubject());

            Date nowDate = new Date();
            Date expirationDateFromJwt = claims.getExpiration();

            Optional<UserEntity> user = userRepository.findById(userIdFromJwt);
            UserEntity verifiedUser;

            if (user.isEmpty()) {
                throw new Exception("Użytkownik nie istnieje");
            } else {
                verifiedUser = user.get();
            }

            if (expirationDateFromJwt.before(nowDate)) {
                throw new Exception("JWT jest przeterminowany");
            }

            Boolean isUserAdmin = verifiedUser.getIsAdmin();

            switch (whoCanAccess) {
                case EVERYONE:
                    return true;

                case USER:
                    return !isUserAdmin;

                case ADMIN:
                    return isUserAdmin;

                default:
                    return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

    public void access(String token, WhoCanAccess whoCanAccess) throws AuthException {
        if (!authorize(token, whoCanAccess)) {
            throw new AuthException("Nie masz uprawnień, aby dostać się do tego zasobu.");
        }
    }

    public UserEntity getUserFromToken(String token) throws Exception {
        if (token != null && token.startsWith("Bearer")) {
            token = token.substring(7, token.length());
        } else
            throw new Exception("Nieprawidłowy token.");

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Long userIdFromJwt = Long.valueOf(claims.getSubject());

        Date nowDate = new Date();
        Date expirationDateFromJwt = claims.getExpiration();

        Optional<UserEntity> user = userRepository.findById(userIdFromJwt);
        UserEntity verifiedUser;

        if (user.isEmpty()) {
            throw new Exception("Użytkownik nie istnieje");
        } else {
            verifiedUser = user.get();
        }

        return verifiedUser;
    }

    public Long getUserIdFromToken(String token) throws Exception {
        if (token != null && token.startsWith("Bearer")) {
            token = token.substring(7, token.length());
        } else
            throw new Exception("Nieprawidłowy token.");

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Long userIdFromJwt = Long.valueOf(claims.getSubject());

        return userIdFromJwt;
    }
}