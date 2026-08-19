import java.util.ArrayList;

public class ScanResult {

    private int totalFiles;
    private ArrayList<DuplicateGroup> duplicateGroups;

    public ScanResult(
            int totalFiles,
            ArrayList<DuplicateGroup> duplicateGroups) {

        this.totalFiles = totalFiles;
        this.duplicateGroups = duplicateGroups;
    }

    public int getTotalFiles() {
        return totalFiles;
    }

    public ArrayList<DuplicateGroup> getDuplicateGroups() {
        return duplicateGroups;
    }

    public int getDuplicateFileCount() {

        int count = 0;

        for (DuplicateGroup group : duplicateGroups) {

            count += group.getFiles().size();
        }

        return count;
    }

    public long getWastedSpace() {

        long total = 0;

        for (DuplicateGroup group : duplicateGroups) {

            total += group.getWastedSpace();
        }

        return total;
    }
}