import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

/**
 * Created by Gilles Callebaut on 4/02/2016.
 */
public class Main {
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

        System.out.println("-------------------- Symm Key -----------------");
        String s = "Hallo";
        System.out.println("Give passphrase/PIN: ");
        String sKey = new Scanner(System.in).nextLine();
        String sEncrypt = Crypto.encrypt(s,sKey,sr, true);

        System.out.println(sEncrypt);
        //String sDecrypt = Crypto.decrypt(sEncrypt, true);
        System.out.println("-----------------------------------------------\n");
    }
}
