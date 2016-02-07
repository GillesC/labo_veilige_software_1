import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * Created by Gilles Callebaut on 4/02/2016.
 */
public class Crypto {
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
        return toBase64String(digest);
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

    public static String encrypt(String s, String key, SecureRandom sr, boolean symmetric) {
        Cipher c =null;
        if (symmetric) {
            try {
                DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
                SecretKey sk = SecretKeyFactory.getInstance("DES").generateSecret(desKeySpec);
                c = Cipher.getInstance("DES");
                c.init(Cipher.ENCRYPT_MODE, sk);
            } catch (NoSuchAlgorithmException e) {
                System.err.println(e.getLocalizedMessage());
                return null;
            } catch (NoSuchPaddingException e) {
                System.err.println(e.getLocalizedMessage());
                return null;
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                System.err.println(e.getLocalizedMessage());
                return null;
            }
        }
        if (c != null) {
            try {
                return toBase64String(c.doFinal(s.getBytes()));
            } catch (IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String toBase64String(byte[] b){
        return String.valueOf(Base64.getEncoder().encode(b));
    }
}
