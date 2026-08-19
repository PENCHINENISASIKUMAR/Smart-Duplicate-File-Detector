import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ScanResult scanResult = null;
        File currentFolder = null;

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("     SMART DUPLICATE FILE DETECTOR");
            System.out.println("========================================");

            System.out.println("1. Scan Folder");
            System.out.println("2. View Duplicate Groups");
            System.out.println("3. View Scan Summary");
            System.out.println("4. Clean Duplicates");
            System.out.println("5. Restore Backup");
            System.out.println("6. Exit");

            System.out.print("\nEnter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            // ========================================
            // 1. SCAN FOLDER
            // ========================================

            if (choice == 1) {

                System.out.print("Enter folder path: ");

                String folderPath = scanner.nextLine();

                // Remove quotes if user enters them
                folderPath = folderPath.replace("\"", "");

                File folder = new File(folderPath);

                if (!folder.exists()) {

                    System.out.println(
                            "Folder does not exist."
                    );

                    continue;
                }

                if (!folder.isDirectory()) {

                    System.out.println(
                            "The path is not a folder."
                    );

                    continue;
                }

                System.out.println();
                System.out.println(
                        "Scanning folder..."
                );

                DuplicateDetector detector =
                        new DuplicateDetector();

                scanResult =
                        detector.findDuplicates(folder);

                // Save current folder
                currentFolder = folder;

                System.out.println();
                System.out.println(
                        "Scan completed successfully."
                );

                System.out.println(
                        "Duplicate Groups Found: "
                                + scanResult
                                .getDuplicateGroups()
                                .size()
                );

                // Generate report
                ReportGenerator reportGenerator =
                        new ReportGenerator();

                boolean reportCreated =
                        reportGenerator.generateReport(
                                folder,
                                scanResult
                        );

                if (reportCreated) {

                    System.out.println(
                            "Report generated: "
                                    + "duplicate_report.txt"
                    );
                }
            }

            // ========================================
            // 2. VIEW DUPLICATE GROUPS
            // ========================================

            else if (choice == 2) {

                if (scanResult == null) {

                    System.out.println(
                            "Please scan a folder first."
                    );

                    continue;
                }

                System.out.println();
                System.out.println(
                        "========================================"
                );
                System.out.println(
                        "          DUPLICATE GROUPS"
                );
                System.out.println(
                        "========================================"
                );

                if (scanResult
                        .getDuplicateGroups()
                        .isEmpty()) {

                    System.out.println(
                            "No duplicate files found."
                    );

                } else {

                    int groupNumber = 1;

                    for (DuplicateGroup group :
                            scanResult
                                    .getDuplicateGroups()) {

                        System.out.println();
                        System.out.println(
                                "Duplicate Group "
                                        + groupNumber
                        );

                        System.out.println(
                                "----------------------------------------"
                        );

                        int fileNumber = 1;

                        for (File file :
                                group.getFiles()) {

                            System.out.println(
                                    fileNumber
                                            + ". "
                                            + file.getAbsolutePath()
                            );

                            fileNumber++;
                        }

                        System.out.println();

                        System.out.println(
                                "Size: "
                                        + formatSize(
                                        group.getFileSize()
                                )
                        );

                        System.out.println(
                                "Wasted Space: "
                                        + formatSize(
                                        group.getWastedSpace()
                                )
                        );

                        System.out.println(
                                "Hash: "
                                        + group.getHash()
                        );

                        groupNumber++;
                    }
                }
            }

            // ========================================
            // 3. VIEW SCAN SUMMARY
            // ========================================

            else if (choice == 3) {

                if (scanResult == null) {

                    System.out.println(
                            "Please scan a folder first."
                    );

                    continue;
                }

                System.out.println();
                System.out.println(
                        "========================================"
                );
                System.out.println(
                        "             SCAN SUMMARY"
                );
                System.out.println(
                        "========================================"
                );

                System.out.println(
                        "Files Scanned       : "
                                + scanResult.getTotalFiles()
                );

                System.out.println(
                        "Duplicate Groups    : "
                                + scanResult
                                .getDuplicateGroups()
                                .size()
                );

                System.out.println(
                        "Duplicate Files     : "
                                + scanResult
                                .getDuplicateFileCount()
                );

                System.out.println(
                        "Wasted Storage      : "
                                + formatSize(
                                scanResult.getWastedSpace()
                        )
                );
            }

            // ========================================
            // 4. CLEAN DUPLICATES
            // ========================================

            else if (choice == 4) {

                if (scanResult == null) {

                    System.out.println(
                            "Please scan a folder first."
                    );

                    continue;
                }

                if (scanResult
                        .getDuplicateGroups()
                        .isEmpty()) {

                    System.out.println(
                            "No duplicates available."
                    );

                    continue;
                }

                FileCleaner cleaner =
                        new FileCleaner();

                System.out.println();
                System.out.println(
                        "========================================"
                );
                System.out.println(
                        "           CLEAN DUPLICATES"
                );
                System.out.println(
                        "========================================"
                );

                int groupNumber = 1;

                for (DuplicateGroup group :
                        scanResult
                                .getDuplicateGroups()) {

                    System.out.println();
                    System.out.println(
                            "Duplicate Group "
                                    + groupNumber
                    );

                    System.out.println(
                            "----------------------------------------"
                    );

                    int fileNumber = 1;

                    for (File file :
                            group.getFiles()) {

                        System.out.println(
                                fileNumber
                                        + ". "
                                        + file.getName()
                        );

                        fileNumber++;
                    }

                    System.out.println();

                    System.out.println(
                            "Wasted Space: "
                                    + formatSize(
                                    group.getWastedSpace()
                            )
                    );

                    System.out.print(
                            "Enter file number to KEEP "
                                    + "(0 = skip): "
                    );

                    int keepChoice =
                            scanner.nextInt();

                    scanner.nextLine();

                    if (keepChoice == 0) {

                        System.out.println(
                                "Skipped."
                        );

                        groupNumber++;
                        continue;
                    }

                    if (keepChoice < 1 ||
                            keepChoice >
                                    group.getFiles().size()) {

                        System.out.println(
                                "Invalid file number."
                        );

                        groupNumber++;
                        continue;
                    }

                    File keepFile =
                            group.getFiles()
                                    .get(keepChoice - 1);

                    System.out.println();
                    System.out.println(
                            "File to keep:"
                    );

                    System.out.println(
                            keepFile.getAbsolutePath()
                    );

                    System.out.print(
                            "Move other duplicates "
                                    + "to backup? (Y/N): "
                    );

                    String answer =
                            scanner.nextLine();

                    if (!answer.equalsIgnoreCase("Y")) {

                        System.out.println(
                                "Cleanup cancelled."
                        );

                        groupNumber++;
                        continue;
                    }

                    File backupFolder =
                            new File(
                                    keepFile.getParentFile(),
                                    "DuplicateBackup"
                            );

                    for (int i = 0;
                         i < group.getFiles().size();
                         i++) {

                        if (i == keepChoice - 1) {
                            continue;
                        }

                        File duplicateFile =
                                group.getFiles().get(i);

                        boolean moved =
                                cleaner.moveToBackup(
                                        duplicateFile,
                                        backupFolder
                                );

                        if (moved) {

                            System.out.println(
                                    "Moved to backup: "
                                            + duplicateFile.getName()
                            );
                        }
                    }

                    System.out.println();
                    System.out.println(
                            "Cleanup completed."
                    );

                    groupNumber++;
                }
            }

            // ========================================
            // 5. RESTORE BACKUP
            // ========================================

            else if (choice == 5) {

                if (currentFolder == null) {

                    System.out.print(
                            "Enter folder path: "
                    );

                    String folderPath =
                            scanner.nextLine();

                    folderPath =
                            folderPath.replace(
                                    "\"",
                                    ""
                            );

                    currentFolder =
                            new File(folderPath);
                }

                if (!currentFolder.exists() ||
                        !currentFolder.isDirectory()) {

                    System.out.println(
                            "Invalid folder."
                    );

                    currentFolder = null;
                    continue;
                }

                File backupFolder =
                        new File(
                                currentFolder,
                                "DuplicateBackup"
                        );

                BackupManager backupManager =
                        new BackupManager();

                File[] backupFiles =
                        backupManager
                                .getBackupFiles(
                                        backupFolder
                                );

                if (backupFiles.length == 0) {

                    System.out.println();
                    System.out.println(
                            "No backup files found."
                    );

                    continue;
                }

                System.out.println();
                System.out.println(
                        "========================================"
                );
                System.out.println(
                        "           RESTORE BACKUP"
                );
                System.out.println(
                        "========================================"
                );

                for (int i = 0;
                     i < backupFiles.length;
                     i++) {

                    System.out.println(
                            (i + 1)
                                    + ". "
                                    + backupFiles[i].getName()
                    );
                }

                System.out.println(
                        "0. Cancel"
                );

                System.out.print(
                        "\nEnter file number to restore: "
                );

                int restoreChoice =
                        scanner.nextInt();

                scanner.nextLine();

                if (restoreChoice == 0) {

                    System.out.println(
                            "Restore cancelled."
                    );

                    continue;
                }

                if (restoreChoice < 1 ||
                        restoreChoice >
                                backupFiles.length) {

                    System.out.println(
                            "Invalid choice."
                    );

                    continue;
                }

                File selectedFile =
                        backupFiles[
                                restoreChoice - 1
                        ];

                System.out.println();
                System.out.println(
                        "File selected: "
                                + selectedFile.getName()
                );

                System.out.print(
                        "Restore this file? (Y/N): "
                );

                String answer =
                        scanner.nextLine();

                if (!answer.equalsIgnoreCase("Y")) {

                    System.out.println(
                            "Restore cancelled."
                    );

                    continue;
                }

                boolean restored =
                        backupManager.restoreFile(
                                selectedFile,
                                currentFolder
                        );

                if (restored) {

                    System.out.println();
                    System.out.println(
                            "File restored successfully."
                    );

                } else {

                    System.out.println();
                    System.out.println(
                            "Could not restore the file."
                    );
                }
            }

            // ========================================
            // 6. EXIT
            // ========================================

            else if (choice == 6) {

                System.out.println();
                System.out.println(
                        "Thank you for using "
                                + "Smart Duplicate File Detector!"
                );

                break;
            }

            // ========================================
            // INVALID CHOICE
            // ========================================

            else {

                System.out.println();
                System.out.println(
                        "Invalid choice. Please try again."
                );
            }
        }

        scanner.close();
    }

    // ========================================
    // FORMAT FILE SIZE
    // ========================================

    private static String formatSize(long bytes) {

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
                    bytes /
                            (1024.0 * 1024.0)
            );
        }

        return String.format(
                "%.2f GB",
                bytes /
                        (1024.0 * 1024.0 * 1024.0)
        );
    }
}

