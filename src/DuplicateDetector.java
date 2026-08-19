import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class DuplicateDetector {

    public ScanResult findDuplicates(File folder) {

        FileGrouper grouper = new FileGrouper();

        HashMap<Long, ArrayList<File>> groups =
                grouper.groupBySize(folder);

        int totalFiles = 0;

        for (ArrayList<File> files : groups.values()) {
            totalFiles += files.size();
        }

        ArrayList<DuplicateGroup> duplicateGroups =
                new ArrayList<>();

        for (Long size : groups.keySet()) {

            ArrayList<File> files = groups.get(size);

            if (files.size() > 1) {

                HashMap<String, ArrayList<File>> hashGroups =
                        new HashMap<>();

                for (File file : files) {

                    String hash =
                            HashUtil.calculateHash(file);

                    if (hash == null) {
                        continue;
                    }

                    if (!hashGroups.containsKey(hash)) {

                        hashGroups.put(
                                hash,
                                new ArrayList<>()
                        );
                    }

                    hashGroups.get(hash).add(file);
                }

                for (String hash : hashGroups.keySet()) {

                    ArrayList<File> duplicateFiles =
                            hashGroups.get(hash);

                    if (duplicateFiles.size() > 1) {

                        DuplicateGroup group =
                                new DuplicateGroup(
                                        duplicateFiles,
                                        size,
                                        hash
                                );

                        duplicateGroups.add(group);
                    }
                }
            }
        }

        return new ScanResult(
                totalFiles,
                duplicateGroups
        );
    }
}