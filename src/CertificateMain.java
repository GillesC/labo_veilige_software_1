import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

/**
 * Created by Lukas on 25-Feb-16.
 */
public class CertificateMain {

    public static void main(String[] args) {
        KeyStore keyStore1 = null;
        KeyStore keyStore2 = null;
        PrivateKey privateKeyWerner = null;
        PrivateKey privateKeyFreya = null;
        PublicKey publicKeyFreya = null;

        try {
            // Werner-side
            keyStore1 = KeyStore.getInstance("JKS");
            String fileNameStore1 = new File("data\\store1.jks").getAbsolutePath();

            char[] password = "password123".toCharArray();
            FileInputStream fis = new FileInputStream(fileNameStore1);
            keyStore1.load(fis, password);
            fis.close();
            privateKeyWerner = (PrivateKey) keyStore1.getKey("werner", "password123".toCharArray());
            Certificate cert = (Certificate) keyStore1.getCertificate("freya"); // certificaat van freya
            publicKeyFreya = (PublicKey) cert.getPublicKey();

            String message = "Dag Freya. Werner hier. Wanneer spreken we af?";
            Cipher eCipher = Crypto.getRSACipher();
            String sEncrypt = Crypto.encrypt(message, publicKeyFreya, eCipher);
            System.out.println("Encrypted text: " + sEncrypt);

            // Freya-side
            keyStore2 = KeyStore.getInstance("JKS");
            String fileNameStore2 = new File("data\\store2.jks").getAbsolutePath();
            ;
            password = "password456".toCharArray();
            fis = new FileInputStream(fileNameStore2);
            keyStore2.load(fis, password);
            fis.close();
            privateKeyFreya = (PrivateKey) keyStore2.getKey("freya", "".toCharArray());


            String sDecrypt = Crypto.decrypt(sEncrypt, privateKeyFreya, Crypto.getRSACipher(), null);
            System.out.println("Decrypted text: " + sDecrypt);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
