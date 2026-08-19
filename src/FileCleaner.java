import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileCleaner {

    public boolean moveToBackup(
            File file,
            File backupFolder) {

        try {

            if (!backupFolder.exists()) {

                backupFolder.mkdirs();
            }

            File destination =
                    new File(
                            backupFolder,
                            file.getName()
                    );

            // Avoid overwriting another file
            int count = 1;

            while (destination.exists()) {

                String name =
                        file.getName();

                int dot =
                        name.lastIndexOf(".");

                String newName;

                if (dot > 0) {

                    newName =
                            name.substring(0, dot)
                            + "_"
                            + count
                            + name.substring(dot);

                } else {

                    newName =
                            name
                            + "_"
                            + count;
                }

                destination =
                        new File(
                                backupFolder,
                                newName
                        );

                count++;
            }

            Files.move(
                    file.toPath(),
                    destination.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Could not move file: "
                            + file.getAbsolutePath()
            );

            return false;
        }
    }
}