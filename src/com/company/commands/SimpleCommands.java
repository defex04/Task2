package com.company.commands;

import java.util.List;

public interface SimpleCommands {

    void rename();
    void remove();

    void cat(String name);
    void size();

    List<String> ls();
    List<String> sort();
}
