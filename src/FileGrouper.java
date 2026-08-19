import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;

public class FileGrouper {

    public HashMap<Long, ArrayList<File>> groupBySize(File folder) {

        HashMap<Long, ArrayList<File>> groups = new HashMap<>();

        scanFolder(folder, groups);

        return groups;
    }

    private void scanFolder(
            File folder,
            HashMap<Long, ArrayList<File>> groups) {

        File[] files = folder.listFiles();

        if (files == null) {
            return;
        }

        for (File file : files) {

            if (file.isFile()) {

                long size = file.length();

                if (!groups.containsKey(size)) {

                    groups.put(
                        size,
                        new ArrayList<>()
                    );
                }

                groups.get(size).add(file);

            } else if (file.isDirectory()) {

                scanFolder(file, groups);
            }
        }
    }
}