package com.lecturefeed.authentication.jwt;

import com.lecturefeed.core.HomeDirHandler;
import lombok.AccessLevel;
import lombok.Getter;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Getter
public class RSAKeyHandler{

    private static final String PRIVATE_KEY_FILENAME = "rsa.key";
    private static final String PUBLIC_KEY_FILENAME = "rsa.pub";
    private static final Integer KEY_SIZE = 2048;
    @Getter(value = AccessLevel.PROTECTED)
    private static RSAPrivateKey privateKey;
    @Getter(value = AccessLevel.PROTECTED)
    private static RSAPublicKey publicKey;

    protected static void initKeys(){
        initRSA();
    }

    private static void initRSA(){
        Path privateKey = Paths.get(HomeDirHandler.getLectureFeedPath().toString(), PRIVATE_KEY_FILENAME);
        Path publicKey = Paths.get(HomeDirHandler.getLectureFeedPath().toString(), PUBLIC_KEY_FILENAME);
        if(Files.notExists(privateKey)){
            initRSAKey(privateKey, publicKey);
        }
        if(Files.notExists(publicKey)){
            throw new RSAKeyHandlerRuntimeException(String.format("Public key %s are not found. Please delete the private key %s and restart the application to solve the problem.", publicKey, privateKey));
        }
        try {
            RSAKeyHandler.privateKey = loadPrivateKeyFromFile(privateKey);
            RSAKeyHandler.publicKey = loadPublicKeyFromFile(publicKey);
        }catch (Exception e){
            throw new RSAKeyHandlerRuntimeException(String.format("Can not load rsa keys. Please delete the private key %s and public key %s.", publicKey, privateKey));
        }
    }

    private static void initRSAKey(Path privateKeyPath, Path publicKeyPath){
        try{
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(KEY_SIZE);
            KeyPair pair = generator.generateKeyPair();
            saveKey(privateKeyPath, pair.getPrivate());
            saveKey(publicKeyPath, pair.getPublic());
        } catch (Exception e) {
            throw new RSAKeyHandlerRuntimeException(e);
        }
    }

    private static void saveKey(Path keyPath, Key key){
        try (FileOutputStream fos = new FileOutputStream(keyPath.toString())){
            fos.write(key.getEncoded());
        } catch (Exception e) {
            throw new RSAKeyHandlerRuntimeException(e);
        }
    }

    private static RSAPrivateKey loadPrivateKeyFromFile(Path privateKeyPath) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(privateKeyPath.toString()));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) kf.generatePrivate(spec);
    }

    private static RSAPublicKey loadPublicKeyFromFile(Path publicKeyPath) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(publicKeyPath.toString()));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) kf.generatePublic(spec);
    }

    static class RSAKeyHandlerRuntimeException extends RuntimeException{
        public RSAKeyHandlerRuntimeException(Exception e){
            super(e);
        }
        public RSAKeyHandlerRuntimeException(String s){
            super(s);
        }
    }


}
