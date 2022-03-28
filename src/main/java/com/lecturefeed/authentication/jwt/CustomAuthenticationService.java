package com.lecturefeed.authentication.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lecturefeed.model.token.TokenClaim;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationService extends RSAKeyHandler {

    private static Algorithm algorithmRS;

    public static void init(){
        initKeys();
        algorithmRS = Algorithm.RSA256(getPublicKey(), getPrivateKey());
    }

    public String generateToken(){
        return generateToken(new HashMap<>());
    }

    public String generateToken(Map<TokenClaim, Object> payloadClaims) {
        try {
            return JWT.create()
                    .withPayload(payloadClaims.entrySet().
                            stream().
                            collect(Collectors.toMap(e -> e.getKey().getValue(), Map.Entry::getValue)))
                    .withIssuer("auth0")
                    .sign(algorithmRS);
        } catch (JWTCreationException exception){
           throw new JWTRuntimeException(exception);
        }
    }

    public DecodedJWT verifyToken(String token){
        JWTVerifier verifier = JWT.require(algorithmRS)
                .withIssuer("auth0")
                .build(); //Reusable verifier instance
        return verifier.verify(token);
    }

    static class JWTRuntimeException extends RuntimeException{
        public JWTRuntimeException(Exception e){
            super(e);
        }
    }

}
