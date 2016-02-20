import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BASE64DecoderStream;
import com.sun.xml.internal.messaging.saaj.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

/**
 * Created by Gilles Callebaut on 4/02/2016.
 */
public class Main {
    private static final int NUM_ITERATIONS_TEST = 1;


    public static void main(String[] args) {
        Person person1 = new Person("Gilles","Lokeren","0486459157");
        Person person1Fake = new Person("Gilles","Gent","0486459123");

        System.out.println("------------------- MD5 Hash ------------------");
        System.out.println("Person1:\t\t"+Crypto.digest(person1.getBytes(),"MD5"));
        System.out.println("Person1Fake:\t"+Crypto.digest(person1Fake.getBytes(),"MD5"));
        System.out.println("-----------------------------------------------\n");

        System.out.println("------------------ SHA-1 Hash -----------------");
        System.out.println("Person1:\t\t"+Crypto.digest(person1.getBytes(),"SHA-1"));
        System.out.println("Person1Fake:\t"+Crypto.digest(person1Fake.getBytes(),"SHA-1"));
        System.out.println("-----------------------------------------------\n");

        System.out.println("---------------------- RNG --------------------");
        System.out.println("Default:\t"+Crypto.getRandom(false).getAlgorithm()+" van "+Crypto.getRandom(false).getProvider());
        System.out.println("SHA1RNG:\t"+Crypto.getRandom(true).getAlgorithm()+" van "+Crypto.getRandom(true).getProvider());
        SecureRandom sr = Crypto.getRandom(true);
        //SecureRandom sr2 = Crypto.getRandom(true);
        //sr1.setSeed("foo-bar".getBytes());
        //sr2.setSeed("foo-bar".getBytes());
        //System.out.println(sr1.nextInt());
        //System.out.println(sr2.nextInt());
        System.out.println("-----------------------------------------------\n");

        System.out.println("-------------------- Symmetric Key ------------");
        long totalTimeSym = 0;
        long startTime, stopTime;
        for(int i=0;i<NUM_ITERATIONS_TEST;i++) {
            startTime = System.nanoTime();
            String encryptMe = "Hallo";
            System.out.println("Text to encrypt: " + encryptMe);
            SecretKey sKey = Crypto.generateSecretKey(sr);
            Cipher eCipher = Crypto.getDESCipher();
            String sEncrypt = Crypto.encrypt(encryptMe, sKey, eCipher);
            System.out.println("Encrypted text: " + sEncrypt);
            String sDecrypt = Crypto.decrypt(sEncrypt, sKey, Crypto.getDESCipher(), eCipher.getIV());
            System.out.println("Decrypted text: " + sDecrypt);
            stopTime = System.nanoTime();
            totalTimeSym += stopTime-startTime;
        }
        System.out.println("Mean execution time (per cycle) "+ (totalTimeSym/1000000) +" ms over "+NUM_ITERATIONS_TEST+ " tests.");
        System.out.println("-----------------------------------------------\n");


        System.out.println("-------------------- Asymmetric Key ------------");
        for(int i=0;i<NUM_ITERATIONS_TEST;i++) {
            startTime = System.nanoTime();
            String encryptMe = "Hallo";
            System.out.println("Text to encrypt: " + encryptMe);
            KeyPair keyPair = Crypto.generateKeyPair(sr);
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();
            Cipher eCipher = Crypto.getRSACipher();
            String sEncrypt = Crypto.encrypt(encryptMe, publicKey, eCipher);
            System.out.println("Encrypted text: " + sEncrypt);
            String sDecrypt = Crypto.decrypt(sEncrypt, privateKey, Crypto.getRSACipher(), null);
            System.out.println("Decrypted text: " + sDecrypt);
            System.out.println("Public key: "+ publicKey+ "\n KEY itself: " + Crypto.encodeToBase64String(publicKey.getEncoded()));
            stopTime = System.nanoTime();
            totalTimeSym += stopTime-startTime;
       }
        System.out.println("Mean execution time (per cycle) "+ (totalTimeSym/1000000) +" ms over "+NUM_ITERATIONS_TEST+ " tests.");
        System.out.println("-----------------------------------------------\n");

    }

}
