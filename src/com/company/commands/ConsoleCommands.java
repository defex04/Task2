package com.company.commands;

import com.company.CurrentPath;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class ConsoleCommands implements FileArchiving, SimpleCommands {

    @Override
    public void archive() {

    }

    @Override
    public void unarhive() {

    }

    @Override
    public void rename(String oldName, String newName) {

        File original = CurrentPath.currentPath.resolve(oldName).toFile();
        File newFile = CurrentPath.currentPath.resolve(newName).toFile();

        if (original.exists() & original.isFile()) {
            original.renameTo(newFile);
        }
        else {
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
    public void cat(String name) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(CurrentPath.currentPath + "/" + name).normalize()));
        System.out.println(content);
    }


    @Override
    public void size(String name) throws IOException {
        System.out.println(Files.size(CurrentPath.currentPath));
    }

    @Override
    public void ls(String key) throws IOException {
        if (key.equals("-d")) {
            Files.find(CurrentPath.currentPath, 1, (p, bfa) -> bfa.isDirectory()).forEach(System.out::println);
        }
        else if (key.equals("-f")) {
            Files.find(CurrentPath.currentPath, 1, (p, bfa) -> bfa.isRegularFile()).forEach(System.out::println);
        }
        //else if (key.equals("-fs")) {
          //  Files.find(currentPath, 1, (p, bfa) -> p.).forEach(System.out::println);
        //}
        else if (key.equals("-all")) {
                Files.find(CurrentPath.currentPath, 1, (p, bfa) -> true).forEach(System.out::println);
        }
    }

}
