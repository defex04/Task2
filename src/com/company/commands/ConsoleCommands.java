package com.company.commands;

import java.util.List;

public class ConsoleCommands implements FileArchiving, SimpleCommands {
    @Override
    public void archive() {

    }

    @Override
    public void unarhive() {

    }

    @Override
    public void rename() {

    }

    @Override
    public void remove() {

    }

    @Override
    public void cat(String name) {
        System.out.println(name);
    }


    @Override
    public void size() {

    }

    @Override
    public List<String> ls() {
        return null;
    }

    @Override
    public List<String> sort() {
        return null;
    }
}
