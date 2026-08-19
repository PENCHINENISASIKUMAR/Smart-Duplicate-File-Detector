import java.io.File;
import java.util.ArrayList;

public class DuplicateGroup {

    private ArrayList<File> files;
    private long fileSize;
    private String hash;

    public DuplicateGroup(
            ArrayList<File> files,
            long fileSize,
            String hash) {

        this.files = files;
        this.fileSize = fileSize;
        this.hash = hash;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getHash() {
        return hash;
    }

    public long getWastedSpace() {

        return fileSize * (files.size() - 1);
    }
}