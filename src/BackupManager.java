
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class BackupManager {

    public File[] getBackupFiles(File backupFolder) {

        if (!backupFolder.exists() ||
                !backupFolder.isDirectory()) {

            return new File[0];
        }

        File[] files = backupFolder.listFiles();

        if (files == null) {
            return new File[0];
        }

        return files;
    }

    public boolean restoreFile(
            File backupFile,
            File originalFolder) {

        try {

            if (!originalFolder.exists()) {
                originalFolder.mkdirs();
            }

            File destination =
                    new File(
                            originalFolder,
                            backupFile.getName()
                    );

            // Avoid overwriting an existing file
            if (destination.exists()) {

                String name =
                        backupFile.getName();

                int dot =
                        name.lastIndexOf(".");

                String newName;

                if (dot > 0) {

                    newName =
                            name.substring(0, dot)
                                    + "_restored"
                                    + name.substring(dot);

                } else {

                    newName =
                            name + "_restored";
                }

                destination =
                        new File(
                                originalFolder,
                                newName
                        );
            }

            Files.move(
                    backupFile.toPath(),
                    destination.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Could not restore file: "
                            + backupFile.getName()
            );

            return false;
        }
    }
}
