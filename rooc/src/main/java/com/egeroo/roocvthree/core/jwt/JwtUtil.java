package com.egeroo.roocvthree.core.jwt;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import com.egeroo.roocvthree.core.error.CoreException;
import com.egeroo.roocvthree.user.User;

import io.jsonwebtoken.*;
//import io.jsonwebtoken.impl.crypto.MacProvider;
import io.jsonwebtoken.security.Keys;



public class JwtUtil {

    @Value("${jwt.secret}")
    //private String secret;
    
    //private static final Key secret = MacProvider.generateKey(SignatureAlgorithm.HS256);
    private static final Key secret = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final byte[] secretBytes = secret.getEncoded();
    
    //private static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    
    private static final String base64SecretBytes = Base64.getEncoder().encodeToString(secretBytes);
    
	//We will sign our JWT with our ApiKey secret
    //byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(apiKey.getSecret());
    //Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     * 
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    @SuppressWarnings("deprecation")
	public String createJWT(String id, String issuer, String subject, long ttlMillis) {
    	 
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
     
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
     
        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64SecretBytes);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
     
        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                                    .setIssuedAt(now)
                                    .setSubject(subject)
                                    .setIssuer(issuer)
                                    .signWith(signatureAlgorithm, signingKey);
     
        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
        long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            System.out.println("exp: " + exp);
            builder.setExpiration(exp);
        }
     
        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }
    
    public Claims parseJWT(String jwt) {
    	
    	try {
    		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        	//We will sign our JWT with our ApiKey secret
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64SecretBytes);
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
            
            //This line will throw an exception if it is not a signed JWS (as expected)
            Claims claims = Jwts.parser()         
               .setSigningKey(signingKey)
               .parseClaimsJws(jwt).getBody();
            /*System.out.println("ID: " + claims.getId());
            System.out.println("Subject: " + claims.getSubject());
            System.out.println("Issuer: " + claims.getIssuer());
            System.out.println("Expiration: " + claims.getExpiration());*/
            return claims;
    	} catch (JwtException | ClassCastException e) {
    		e.printStackTrace();
            //return null;
    		//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine access token not found.");
        	throw new CoreException(HttpStatus.UNAUTHORIZED, "Invalid token signature");
        }
    	
    	
    }
    
    public User parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            User u = new User();
            u.setUsername(body.getSubject());
            //u.setUserid(Int. Long.parseLong((String) body.get("userId")));
            //u.setRole((String) body.get("role"));

            return u;

        } catch (JwtException | ClassCastException e) {
            //return null;
        	throw new CoreException(HttpStatus.UNAUTHORIZED, "Token Expired");
        }
    }

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
     * User object. Tokens validity is infinite.
     * 
     * @param u the user for which the token will be generated
     * @return the JWT token
     */
    @SuppressWarnings("deprecation")
	public String generateToken(User u) {
        Claims claims = Jwts.claims().setSubject(u.getUsername());
        claims.put("userId", u.getUserid() + "");
        claims.put("role", u.getRoleid());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}