import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.*;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class project1 {

    private static final String ALGORITHM = "DES";

    public static void encrypt(String key, File inputFile, File outputFile) throws Exception {
        key = padKey(key);
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
        System.out.println("File encrypted successfully!");
    }

    public static void decrypt(String key, File inputFile, File outputFile) throws Exception {
        key = padKey(key);
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
        System.out.println("File decrypted successfully!");
    }

    private static String padKey(String key) {
        byte[] paddedKey = Arrays.copyOf(key.getBytes(), 8);
        return new String(paddedKey);
    }

    private static void doCrypto(int cipherMode, String key, File inputFile, File outputFile) throws Exception {
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        KeySpec keySpec = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(keySpec);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(cipherMode, secretKey);

        byte[] inputBytes = new byte[(int) inputFile.length()];
        inputStream.read(inputBytes);

        byte[] outputBytes = cipher.doFinal(inputBytes);

        outputStream.write(outputBytes);

        inputStream.close();
        outputStream.close();
    }

    public static void main(String[] args) {
        String key = "secret"; // 8-byte key
        File inputFile = new File("input.txt");
        File encryptedFile = new File("encrypted_file.des");
        File decryptedFile = new File("decrypted_output.txt");

        try {
            encrypt(key, inputFile, encryptedFile);
            decrypt(key, encryptedFile, decryptedFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
