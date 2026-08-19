import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainGUI extends JFrame {

    private JLabel folderLabel;

    private JLabel filesScannedLabel;
    private JLabel duplicateGroupsLabel;
    private JLabel duplicateFilesLabel;
    private JLabel wastedStorageLabel;

    private JTextArea duplicateArea;

    private File selectedFolder;
    private ScanResult scanResult;

    public MainGUI() {

        setTitle("Smart Duplicate File Detector");

        setSize(900, 750);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        // ========================================
        // MAIN PANEL
        // ========================================

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        // ========================================
        // TITLE
        // ========================================

        JLabel titleLabel =
                new JLabel(
                        "SMART DUPLICATE FILE DETECTOR",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        mainPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        // ========================================
        // CENTER PANEL
        // ========================================

        JPanel centerPanel =
                new JPanel();

        centerPanel.setLayout(
                new BoxLayout(
                        centerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        // ========================================
        // FOLDER
        // ========================================

        folderLabel =
                new JLabel(
                        "Selected Folder: None"
                );

        folderLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        centerPanel.add(folderLabel);

        centerPanel.add(
                Box.createVerticalStrut(15)
        );

        // ========================================
        // BUTTONS
        // ========================================

        JButton browseButton =
                new JButton(
                        "Browse Folder"
                );

        JButton scanButton =
                new JButton(
                        "Scan Folder"
                );

        JButton cleanButton =
                new JButton(
                        "Clean Duplicates"
                );

        JButton restoreButton =
                new JButton(
                        "Restore Backup"
                );

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout()
                );

        buttonPanel.add(
                browseButton
        );

        buttonPanel.add(
                scanButton
        );

        buttonPanel.add(
                cleanButton
        );

        buttonPanel.add(
                restoreButton
        );

        centerPanel.add(buttonPanel);

        centerPanel.add(
                Box.createVerticalStrut(20)
        );

        // ========================================
        // SCAN SUMMARY
        // ========================================

        JLabel summaryTitle =
                new JLabel(
                        "SCAN SUMMARY"
                );

        summaryTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        centerPanel.add(summaryTitle);

        centerPanel.add(
                Box.createVerticalStrut(10)
        );

        filesScannedLabel =
                new JLabel(
                        "Files Scanned: -"
                );

        duplicateGroupsLabel =
                new JLabel(
                        "Duplicate Groups: -"
                );

        duplicateFilesLabel =
                new JLabel(
                        "Duplicate Files: -"
                );

        wastedStorageLabel =
                new JLabel(
                        "Wasted Storage: -"
                );

        centerPanel.add(
                filesScannedLabel
        );

        centerPanel.add(
                duplicateGroupsLabel
        );

        centerPanel.add(
                duplicateFilesLabel
        );

        centerPanel.add(
                wastedStorageLabel
        );

        // ========================================
        // DUPLICATE FILES
        // ========================================

        centerPanel.add(
                Box.createVerticalStrut(20)
        );

        JLabel duplicateTitle =
                new JLabel(
                        "DUPLICATE FILES"
                );

        duplicateTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        centerPanel.add(duplicateTitle);

        centerPanel.add(
                Box.createVerticalStrut(10)
        );

        duplicateArea =
                new JTextArea(
                        15,
                        70
                );

        duplicateArea.setEditable(false);

        duplicateArea.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        13
                )
        );

        duplicateArea.setLineWrap(false);

        JScrollPane scrollPane =
                new JScrollPane(
                        duplicateArea
                );

        centerPanel.add(scrollPane);

        // ========================================
        // ADD CENTER PANEL
        // ========================================

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        // ========================================
        // BROWSE BUTTON
        // ========================================

        browseButton.addActionListener(e -> {

            JFileChooser chooser =
                    new JFileChooser();

            chooser.setFileSelectionMode(
                    JFileChooser.DIRECTORIES_ONLY
            );

            int result =
                    chooser.showOpenDialog(
                            this
                    );

            if (result ==
                    JFileChooser.APPROVE_OPTION) {

                selectedFolder =
                        chooser.getSelectedFile();

                folderLabel.setText(
                        "Selected Folder: "
                                + selectedFolder
                                .getAbsolutePath()
                );

                // Clear previous scan

                scanResult = null;

                filesScannedLabel.setText(
                        "Files Scanned: -"
                );

                duplicateGroupsLabel.setText(
                        "Duplicate Groups: -"
                );

                duplicateFilesLabel.setText(
                        "Duplicate Files: -"
                );

                wastedStorageLabel.setText(
                        "Wasted Storage: -"
                );

                duplicateArea.setText("");
            }
        });

        // ========================================
        // SCAN BUTTON
        // ========================================

        scanButton.addActionListener(e -> {

            if (selectedFolder == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a folder first.",
                        "No Folder Selected",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            scanFolder();
        });

        // ========================================
        // CLEAN BUTTON
        // ========================================

        cleanButton.addActionListener(e -> {

            if (scanResult == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please scan a folder first.",
                        "No Scan Available",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            cleanDuplicates();
        });

        // ========================================
        // RESTORE BUTTON
        // ========================================

        restoreButton.addActionListener(e -> {

            if (selectedFolder == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a folder first.",
                        "No Folder Selected",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            restoreBackup();
        });

        // ========================================
        // ADD MAIN PANEL
        // ========================================

        add(mainPanel);

        setVisible(true);
    }

    // ========================================
    // SCAN FOLDER
    // ========================================

    private void scanFolder() {

        try {

            duplicateArea.setText(
                    "Scanning folder...\n"
            );

            DuplicateDetector detector =
                    new DuplicateDetector();

            scanResult =
                    detector.findDuplicates(
                            selectedFolder
                    );

            // ========================================
            // UPDATE SUMMARY
            // ========================================

            filesScannedLabel.setText(
                    "Files Scanned: "
                            + scanResult.getTotalFiles()
            );

            duplicateGroupsLabel.setText(
                    "Duplicate Groups: "
                            + scanResult
                            .getDuplicateGroups()
                            .size()
            );

            duplicateFilesLabel.setText(
                    "Duplicate Files: "
                            + scanResult
                            .getDuplicateFileCount()
            );

            wastedStorageLabel.setText(
                    "Wasted Storage: "
                            + formatSize(
                            scanResult
                                    .getWastedSpace()
                    )
            );

            // ========================================
            // DISPLAY DUPLICATES
            // ========================================

            duplicateArea.setText("");

            if (scanResult
                    .getDuplicateGroups()
                    .isEmpty()) {

                duplicateArea.append(
                        "No duplicate files found."
                );

            } else {

                int groupNumber = 1;

                for (DuplicateGroup group :
                        scanResult
                                .getDuplicateGroups()) {

                    duplicateArea.append(
                            "Group "
                                    + groupNumber
                                    + "\n"
                    );

                    duplicateArea.append(
                            "----------------------------------------\n"
                    );

                    int fileNumber = 1;

                    for (File file :
                            group.getFiles()) {

                        duplicateArea.append(
                                fileNumber
                                        + ". "
                                        + file.getName()
                                        + "\n"
                        );

                        fileNumber++;
                    }

                    duplicateArea.append(
                            "Size: "
                                    + formatSize(
                                    group.getFileSize()
                            )
                                    + "\n"
                    );

                    duplicateArea.append(
                            "Wasted Space: "
                                    + formatSize(
                                    group.getWastedSpace()
                            )
                                    + "\n"
                    );

                    duplicateArea.append(
                            "Hash: "
                                    + group.getHash()
                                    + "\n\n"
                    );

                    groupNumber++;
                }
            }

            // ========================================
            // GENERATE REPORT
            // ========================================

            ReportGenerator reportGenerator =
                    new ReportGenerator();

            boolean reportCreated =
                    reportGenerator.generateReport(
                            selectedFolder,
                            scanResult
                    );

            String message =
                    "Scan completed successfully!";

            if (reportCreated) {

                message +=
                        "\n\nReport generated:\n"
                                + "duplicate_report.txt";
            }

            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "Scan Complete",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error while scanning:\n"
                            + e.getMessage(),
                    "Scan Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ========================================
    // CLEAN DUPLICATES
    // ========================================

    private void cleanDuplicates() {

        if (scanResult
                .getDuplicateGroups()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No duplicate files found.",
                    "Nothing to Clean",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        FileCleaner cleaner =
                new FileCleaner();

        int cleanedFiles = 0;

        for (DuplicateGroup group :
                scanResult
                        .getDuplicateGroups()) {

            File[] files =
                    group.getFiles()
                            .toArray(
                                    new File[0]
                            );

            if (files.length < 2) {
                continue;
            }

            StringBuilder message =
                    new StringBuilder();

            message.append(
                    "Duplicate Group\n\n"
            );

            for (int i = 0;
                 i < files.length;
                 i++) {

                message.append(
                        (i + 1)
                                + ". "
                                + files[i].getName()
                                + "\n"
                );
            }

            message.append(
                    "\nSelect the file to KEEP:"
            );

            String[] options =
                    new String[files.length];

            for (int i = 0;
                 i < files.length;
                 i++) {

                options[i] =
                        (i + 1)
                                + ". "
                                + files[i].getName();
            }

            int keepChoice =
                    JOptionPane.showOptionDialog(
                            this,
                            message.toString(),
                            "Clean Duplicate Group",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]
                    );

            if (keepChoice == -1) {

                break;
            }

            File keepFile =
                    files[keepChoice];

            int confirmation =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Keep:\n"
                                    + keepFile.getName()
                                    + "\n\n"
                                    + "Move the other duplicate "
                                    + "files to DuplicateBackup?",
                            "Confirm Cleanup",
                            JOptionPane.YES_NO_OPTION
                    );

            if (confirmation !=
                    JOptionPane.YES_OPTION) {

                continue;
            }

            File backupFolder =
                    new File(
                            keepFile.getParentFile(),
                            "DuplicateBackup"
                    );

            for (int i = 0;
                 i < files.length;
                 i++) {

                if (i == keepChoice) {
                    continue;
                }

                File duplicateFile =
                        files[i];

                boolean moved =
                        cleaner.moveToBackup(
                                duplicateFile,
                                backupFolder
                        );

                if (moved) {

                    cleanedFiles++;
                }
            }
        }

        // Rescan after cleanup

        scanFolder();

        JOptionPane.showMessageDialog(
                this,
                cleanedFiles
                        + " duplicate file(s) "
                        + "moved to DuplicateBackup.",
                "Cleanup Complete",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ========================================
    // RESTORE BACKUP
    // ========================================

    private void restoreBackup() {

        File backupFolder =
                new File(
                        selectedFolder,
                        "DuplicateBackup"
                );

        BackupManager backupManager =
                new BackupManager();

        File[] backupFiles =
                backupManager.getBackupFiles(
                        backupFolder
                );

        if (backupFiles.length == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "No backup files found.",
                    "Restore Backup",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        // ========================================
        // CREATE OPTIONS
        // ========================================

        String[] options =
                new String[backupFiles.length];

        for (int i = 0;
             i < backupFiles.length;
             i++) {

            options[i] =
                    (i + 1)
                            + ". "
                            + backupFiles[i].getName();
        }

        int selected =
                JOptionPane.showOptionDialog(
                        this,
                        "Select a file to restore:",
                        "Restore Backup",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

        // User closed dialog

        if (selected == -1) {
            return;
        }

        File selectedBackup =
                backupFiles[selected];

        // ========================================
        // CONFIRM RESTORE
        // ========================================

        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Restore this file?\n\n"
                                + selectedBackup.getName(),
                        "Confirm Restore",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirmation !=
                JOptionPane.YES_OPTION) {

            return;
        }

        // ========================================
        // RESTORE
        // ========================================

        boolean restored =
                backupManager.restoreFile(
                        selectedBackup,
                        selectedFolder
                );

        if (restored) {

            JOptionPane.showMessageDialog(
                    this,
                    "File restored successfully:\n\n"
                            + selectedBackup.getName(),
                    "Restore Complete",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not restore the file.",
                    "Restore Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        // ========================================
        // RESCAN
        // ========================================

        scanFolder();
    }

    // ========================================
    // FORMAT SIZE
    // ========================================

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

    // ========================================
    // MAIN
    // ========================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new MainGUI()
        );
    }
}

