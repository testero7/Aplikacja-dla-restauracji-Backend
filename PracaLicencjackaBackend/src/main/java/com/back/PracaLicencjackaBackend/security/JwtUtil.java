package com.back.PracaLicencjackaBackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Component
public class JwtUtil {
public static final long jwt_token_validity=5*60*60;

	private String secret="tokenkey";
	public String getUsernameFromToken(String token) {
		 return getClaimFromToken(token, Claims::getSubject);
	}
	public Date getExpirationfromtoken(String token) {
		return (Date) getClaimFromToken(token,Claims::getExpiration);
	}

	private <T> T getClaimFromToken(String token,Function<Claims,T> ClaimResolver) {
		final Claims claims=getAllClaimsFromToken(token);
		return ClaimResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	private Boolean isTokenExpired(String token) {
		 Date expiryDate=getExpirationfromtoken(token);
		 return expiryDate.before(new Date());
	}

	public String generateTokens(UserDetails e){
		Map<String,Object> claims=new HashMap<>();
		return doGenerateToken(claims,e.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		 return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + jwt_token_validity * 1000))
	                .signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	 public Boolean validateToken(String token, UserDetails userDetails){
	        final String username=getUsernameFromToken(token);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }
	
}
