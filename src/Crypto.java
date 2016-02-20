import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BASE64DecoderStream;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BASE64EncoderStream;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;

import static javax.crypto.Cipher.ENCRYPT_MODE;

/**
 * Created by Gilles Callebaut on 4/02/2016.
 */
public class Crypto {
    private static Object DESCipher;

    public static String digest(byte[] bytes, String algorithm) {
        MessageDigest md;
        byte[] digest;
        try {
            md = MessageDigest.getInstance(algorithm);
            md.update(bytes); //supply data to md
            digest = md.digest(); //digest data
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getLocalizedMessage());
            return null;
        }
        return encodeToBase64String(digest);
    }

    public static SecureRandom getRandom(boolean custom) {
        SecureRandom sr;
        try {
            if (custom) sr = SecureRandom.getInstance("SHA1PRNG");
            else sr = new SecureRandom();
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getLocalizedMessage());
            return null;
        }
        return sr;
    }

    public static String encrypt(String encryptMe, Key key, Cipher c) {
        try {
            c.init(ENCRYPT_MODE, key);
            return encodeToBase64String(c.doFinal(encryptMe.getBytes()));
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String decryptMe, Key key, Cipher c, byte[] iv) {
        try {
            byte[] d = decodeToBase64String(decryptMe);
            if(iv!=null) c.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            else c.init(Cipher.DECRYPT_MODE, key);
            return new String(c.doFinal(d), "UTF-8");
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encodeToBase64String(byte[] b){
        return new String(BASE64EncoderStream.encode(b));
    }

    public static byte[] decodeToBase64String(String s){
        return BASE64DecoderStream.decode(s.getBytes());
    }

    public static SecretKey generateSecretKey(SecureRandom sr) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("DES");
            keyGen.init(sr);
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static KeyPair generateKeyPair(SecureRandom sr) {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024,sr);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Cipher getDESCipher() {
        try {
            return  Cipher.getInstance("DES/CBC/PKCS5PAdding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }




    public static Cipher getRSACipher() {
        try {
            return  Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
