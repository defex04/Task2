package com.company.commands;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileArchiving {

    void zip(String name) throws IOException;

    void unzip(String name) throws IOException;
}
