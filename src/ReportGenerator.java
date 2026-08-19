import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReportGenerator {

    public boolean generateReport(
            File folder,
            ScanResult scanResult) {

        File reportFile =
                new File(
                        folder,
                        "duplicate_report.txt"
                );

        try (FileWriter writer =
                     new FileWriter(reportFile)) {

            writer.write(
                    "SMART DUPLICATE FILE DETECTOR\n"
            );

            writer.write(
                    "========================================\n\n"
            );

            writer.write(
                    "Scan Location:\n"
            );

            writer.write(
                    folder.getAbsolutePath()
                            + "\n\n"
            );

            writer.write(
                    "SCAN SUMMARY\n"
            );

            writer.write(
                    "----------------------------------------\n"
            );

            writer.write(
                    "Files Scanned       : "
                            + scanResult.getTotalFiles()
                            + "\n"
            );

            writer.write(
                    "Duplicate Groups    : "
                            + scanResult
                            .getDuplicateGroups()
                            .size()
                            + "\n"
            );

            writer.write(
                    "Duplicate Files     : "
                            + scanResult
                            .getDuplicateFileCount()
                            + "\n"
            );

            writer.write(
                    "Wasted Storage      : "
                            + formatSize(
                            scanResult.getWastedSpace()
                    )
                            + "\n\n"
            );

            writer.write(
                    "DUPLICATE DETAILS\n"
            );

            writer.write(
                    "========================================\n"
            );

            int groupNumber = 1;

            for (DuplicateGroup group :
                    scanResult.getDuplicateGroups()) {

                writer.write(
                        "\nGroup "
                                + groupNumber
                                + "\n"
                );

                writer.write(
                        "----------------------------------------\n"
                );

                for (File file :
                        group.getFiles()) {

                    writer.write(
                            "File: "
                                    + file.getAbsolutePath()
                                    + "\n"
                    );
                }

                writer.write(
                        "Size: "
                                + formatSize(
                                group.getFileSize()
                        )
                                + "\n"
                );

                writer.write(
                        "Wasted Space: "
                                + formatSize(
                                group.getWastedSpace()
                        )
                                + "\n"
                );

                writer.write(
                        "Hash: "
                                + group.getHash()
                                + "\n"
                );

                groupNumber++;
            }

            writer.write(
                    "\n========================================\n"
            );

            writer.write(
                    "Report generated successfully.\n"
            );

            return true;

        } catch (IOException e) {

            System.out.println(
                    "Could not generate report."
            );

            return false;
        }
    }

    private String formatSize(long bytes) {

        if (bytes < 1024) {
            return bytes + " B";
        }

        if (bytes < 1024 * 1024) {

            return String.format(
                    "%.2f KB",
                    bytes / 1024.0
            );
        }

        if (bytes < 1024 * 1024 * 1024) {

            return String.format(
                    "%.2f MB",
                    bytes / (1024.0 * 1024.0)
            );
        }

        return String.format(
                "%.2f GB",
                bytes /
                        (1024.0 * 1024.0 * 1024.0)
        );
    }
}

