package com.lecturefeed.LectureFeedLight.authentication.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Payload;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationService extends RSAKeyHandler {

    private static Algorithm algorithmRS;

    public void init(){
        initKeys();
        algorithmRS = Algorithm.RSA256(getPublicKey(), getPrivateKey());
//        try {
//            Map<String, Object> payloadClaims = new HashMap<>();
//            payloadClaims.put("context", "https://auth0.com/");
//
//            String token = generateToken(payloadClaims);
//            DecodedJWT jwt = verifyToken(token);
//
//            Map<String, Claim> claims = jwt.getClaims();
//            System.out.println("??");
//        }catch (Exception e){
//            System.out.println(e);
//        }
    }

    public String generateToken(){
        return generateToken(new HashMap<>());
    }

    public String generateToken(Map<String, Object> payloadClaims)
    {
        try {
            return JWT.create()
                    .withPayload(payloadClaims)
                    .withIssuer("auth0")
                    .sign(algorithmRS);
        } catch (JWTCreationException exception){
           throw new RuntimeException(exception);
        }
    }

    public DecodedJWT verifyToken(String token){
        JWTVerifier verifier = JWT.require(algorithmRS)
                .withIssuer("auth0")
                .build(); //Reusable verifier instance
        return verifier.verify(token);
    }

}
