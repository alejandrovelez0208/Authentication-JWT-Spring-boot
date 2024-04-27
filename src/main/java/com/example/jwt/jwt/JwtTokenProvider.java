package com.example.jwt.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	@Value("${app.jwt-secret}")
	private String jwtSecret;

	@Value("${app-jwt-expiration-milliseconds}")
	private Long jwtExpirationDate;

	public String generateToken(Authentication authentication) {

		String userName = authentication.getName();

		Date currentDate = new Date();

		Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

		String token = Jwts.builder().claims().subject(userName).issuedAt(currentDate).expiration(expireDate).and()
				.signWith(key()).compact();

		return token;
	}

	public SecretKey key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecret));
	}

	public String getUserName(String token) {
		Jws<Claims> claims = Jwts.parser().verifyWith(key()).build().parseSignedClaims(token);
		String userName = claims.getPayload().getSubject();
		return userName;
	}

	public Boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(key()).build().parse(token);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired:{}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported:{}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty:{}", e.getMessage());
		}
		return false;
	}
}
