package com.company.commands;

import java.io.IOException;

public interface SimpleCommands {

    void rename(String oldName, String newName);

    void remove(String name);

    void cat(String name) throws IOException;

    void size(String name) throws IOException;

    void ls(String key) throws IOException;

    void cd(String key);
}
