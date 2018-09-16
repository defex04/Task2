package com.company.commands;

import com.company.CurrentPath;

import java.io.*;
import java.util.zip.ZipInputStream;
import java.nio.file.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ConsoleCommands implements FileArchiving, SimpleCommands {

    @Override
    public void zip(String name) {

        Path zipPath = Paths.get(CurrentPath.currentPath + "/" + name);
        List<Path> paths = new ArrayList<>();
        try {
            Files.find(zipPath, 999, (p, bfa) -> true).forEach(
                    path -> {
                        if (zipPath != path) {
                            paths.add(path);
                        }
                    }
            );
            FileOutputStream fileOutputStream = new FileOutputStream(CurrentPath.currentPath.toString() + "/" + name + ".zip");
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
            for (Path path : paths) {
                String relative = new File(CurrentPath.currentPath.toString()).toURI().
                        relativize(new File(path.toString()).toURI()).getPath();

                if (path.toFile().isDirectory()) {
                    ZipEntry zipEntry = new ZipEntry(relative);
                    zipOutputStream.putNextEntry(zipEntry);
                } else {
                    ZipEntry zipEntry = new ZipEntry(relative);
                    zipOutputStream.putNextEntry(zipEntry);

                    if (path.toFile().isFile()) {
                        FileInputStream fileInputStream = new FileInputStream(path.toString());
                        byte[] buffer = new byte[fileInputStream.available()];
                        fileInputStream.read(buffer);
                        zipOutputStream.write(buffer);
                    }
                }
                zipOutputStream.closeEntry();
            }
            zipOutputStream.close();
        } catch (IOException e) {
            System.err.println("Incorrect input for zip command");
        }
    }

    @Override
    public void unzip(String name) {

        try {
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(CurrentPath.currentPath.toString() + "/" + name));
            ZipEntry entry;
            String fileName;

            while ((entry = zipInputStream.getNextEntry()) != null) {
                fileName = entry.getName();
                if (!entry.isDirectory()) {
                    FileOutputStream fileOutputStream = new FileOutputStream(CurrentPath.currentPath.toString() + "/unzipped/" + fileName);
                    for (int read = zipInputStream.read(); read != -1; read = zipInputStream.read()) {
                        fileOutputStream.write(read);
                    }
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } else {
                    Path dirPath = Paths.get(CurrentPath.currentPath.toString() + "/unzipped/" + fileName + "/");
                    dirPath.toFile().mkdirs();
                }

                zipInputStream.closeEntry();
            }
        } catch (IOException e) {
            System.err.println("Incorrect input for unzip command");
        }
    }

    @Override
    public void rename(String oldName, String newName) {

        File original = CurrentPath.currentPath.resolve(oldName).toFile();
        File newFile = CurrentPath.currentPath.resolve(newName).toFile();

        if (original.exists() & original.isFile()) {
            original.renameTo(newFile);
        } else {
            System.err.println("No such file");
        }

    }

    @Override
    public void remove(String name) {
        try {
            Files.delete(Paths.get(CurrentPath.currentPath + "/" + name).normalize());
        } catch (NoSuchFileException x) {
            System.err.println("No such file or directory");
        } catch (DirectoryNotEmptyException x) {
            System.err.println("Directory is not empty");
        } catch (IOException x) {
            System.err.println(x);
        }
    }

    @Override
    public void cat(String name) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(CurrentPath.currentPath + "/" + name).normalize()));
        } catch (IOException e) {
            System.err.println("Cat command error");
        }
        System.out.println(content);
    }


    @Override
    public void size(String name) {
        try {
            System.out.println(Files.size(CurrentPath.currentPath));
        } catch (IOException e) {
            System.err.println("No such file");
        }
    }

    @Override
    public void ls(String key) throws IOException {

        switch (key) {
            case "-d":
                Files.find(CurrentPath.currentPath, 1, (p, bfa) -> bfa.isDirectory()).forEach(
                        path -> {
                            if (CurrentPath.currentPath != path) {
                                System.out.println(path);
                            }
                        });
                break;
            case "-f":
                Files.find(CurrentPath.currentPath, 1, (p, bfa) -> bfa.isRegularFile()).forEach(System.out::println);
                break;
            case "-fs":
                List<Path> fileList = new ArrayList<>();
                Files.find(CurrentPath.currentPath, 1, (p, bfa) -> bfa.isRegularFile()).forEach(fileList::add);
                Collections.sort(fileList);
                for (Path file : fileList) {
                    System.out.println(file);
                }
                break;
            case "-all":
                Files.find(CurrentPath.currentPath, 1, (p, bfa) -> true).forEach(
                        path -> {
                            if (CurrentPath.currentPath != path) {
                                System.out.println(path);
                            }
                        }
                );
                break;
            default:
                System.err.println("Incorrect command format!");
                break;
        }
    }

    @Override
    public void cd(String key) {
        switch (key) {
            case "..":
                if (CurrentPath.currentPath.equals(Paths.get("virtualDisk"))) {
                    System.out.println("virtualDisk is root directory");
                } else {
                    CurrentPath.currentPath = Paths.get(CurrentPath.currentPath + "/..").normalize();
                }


                break;
            default:
                Path path = Paths.get(CurrentPath.currentPath + "/" + key);
                if (Files.exists(path)) {
                    CurrentPath.currentPath = path;
                } else {
                    System.err.println("No such directory");
                }
                break;

        }
    }
}
