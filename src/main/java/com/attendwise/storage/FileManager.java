package com.attendwise.storage;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class FileManager {
    private final Path directory;

    public FileManager(String directory) {
        this.directory = Paths.get(directory);
        try {
            Files.createDirectories(this.directory);
        } catch (IOException e) {
            throw new IllegalStateException("Could not create data directory.", e);
        }
    }

    public List<String> read(String fileName) {
        Path file = directory.resolve(fileName);
        try {
            if (!Files.exists(file)) return List.of();
            return Files.readAllLines(file);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read " + fileName, e);
        }
    }

    public void write(String fileName, List<String> lines) {
        try {
            Files.write(directory.resolve(fileName), lines,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new IllegalStateException("Could not write " + fileName, e);
        }
    }
}
