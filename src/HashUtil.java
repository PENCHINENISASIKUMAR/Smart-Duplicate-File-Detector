import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class HashUtil {

    public static String calculateHash(File file) {

        try {

            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            FileInputStream input =
                    new FileInputStream(file);

            byte[] buffer = new byte[4096];

            int bytesRead;

            while ((bytesRead = input.read(buffer)) != -1) {

                digest.update(buffer, 0, bytesRead);
            }

            input.close();

            byte[] hashBytes = digest.digest();

            StringBuilder hash = new StringBuilder();

            for (byte b : hashBytes) {

                hash.append(String.format("%02x", b));
            }

            return hash.toString();

        } catch (Exception e) {

            return null;
        }
    }
}