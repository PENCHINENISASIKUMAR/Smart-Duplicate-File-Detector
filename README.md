# Smart Duplicate File Detector

A Java-based desktop application that scans folders and identifies duplicate files using file content comparison. It helps users find and manage duplicate files efficiently, reducing unnecessary storage usage.

## Features

- Scan a selected folder for duplicate files
- Compare files based on their content
- Identify duplicate files accurately
- Display duplicate file groups
- Simple and user-friendly interface
- Helps reduce unnecessary disk space usage

## Technologies Used

- Java
- Java Swing
- Java NIO / File Handling
- SHA-256 Hashing
- Object-Oriented Programming

## How It Works

1. The user selects a folder to scan.
2. The application searches for files inside the selected folder.
3. Files are grouped based on their size.
4. A SHA-256 hash is generated for the files.
5. Files with the same hash are identified as duplicates.
6. Duplicate files are displayed to the user.
