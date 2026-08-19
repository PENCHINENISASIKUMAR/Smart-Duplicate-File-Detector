import java.io.File;

public class FileScanner {

    public void scanFolder(String folderPath) {

        File folder = new File(folderPath);


        if (!folder.exists()) {
            System.out.println("Folder does not exist.");
            return;
        }

        if (!folder.isDirectory()) {
            System.out.println("The path is not a folder.");
            return;
        }

        File[] files = folder.listFiles();

        if (files == null) {
            System.out.println("Unable to read folder.");
            return;
        }

        for (File file : files) {

            if (file.isFile()) {
          System.out.println(
    file.getName() + " - " + formatSize(file.length())
);
            }
        }
        
    }
    private String formatSize(long bytes) {

    if (bytes < 1024) {
        return bytes + " B";
    }

    if (bytes < 1024 * 1024) {
        return String.format("%.2f KB", bytes / 1024.0);
    }

    if (bytes < 1024 * 1024 * 1024) {
        return String.format("%.2f MB",
                bytes / (1024.0 * 1024.0));
    }

    return String.format("%.2f GB",
            bytes / (1024.0 * 1024.0 * 1024.0));
}
}