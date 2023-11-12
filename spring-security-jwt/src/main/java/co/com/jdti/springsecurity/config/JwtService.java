package co.com.jdti.springsecurity.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

	private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

	public String extractUsername(String jwt) {
		return extractClaim(jwt, Claims::getSubject);
	}

	private <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(jwt);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String jwt) {
		return Jwts.parserBuilder().setSigningKey(KEY)
			.build()
			.parseClaimsJws(jwt)
			.getBody();
	}

	private boolean isTokenExpired(String jwt) {
		return extractExpiration(jwt).before(new Date());
	}

	private Date extractExpiration(String jwt) {
		return extractClaim(jwt, Claims::getExpiration);
	}

	public String generateToken(Map<String, Object> extraClaim, UserDetails userDetails) {
		return Jwts.builder().setClaims(extraClaim)
			.setSubject(userDetails.getUsername())
			.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
			.signWith(KEY, SignatureAlgorithm.HS512).compact();
	}

	public boolean isTokenValid(String jwt, UserDetails userDetails) {
		final String username = extractUsername(jwt);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(jwt);
	}
}
