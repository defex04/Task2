package com.company;

import com.company.commands.ConsoleCommands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args)
            throws ClassNotFoundException, IllegalAccessException, InvocationTargetException {

        CurrentPath.currentPath = Paths.get("virtualDisk");

        String commandFormat = "([a-zA-Z][a-zA-Z0-9]*)";
        String argumentFormat = "([a-zA-Z0-9]*[\\.]?[\\.]?[\\/]?[a-zA-Z0-9]*)";
        String keyFormat = "([\\-][a-zA-Z0-9]*)";

        String oneArgumentCommand = "((^" + commandFormat + "(\\s+)" + argumentFormat + "$))";
        String twoArgumentCommand = "((^" + commandFormat + "(\\s+)" + argumentFormat + "(\\s+)"
                + argumentFormat + "$))";
        String commandWithKey = "((^" + commandFormat + "(\\s+)" + keyFormat + "$))";

        String expression = oneArgumentCommand + "|" + twoArgumentCommand + "|" + commandWithKey;
        Pattern expressionPattern = Pattern.compile(expression);


        while (true) {
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();
            Matcher matcher = expressionPattern.matcher(input);
            List<String> arguments = new ArrayList<>();

            if (matcher.find()) {
                try {
                    if (matcher.group(1) != null) {
                        arguments.add(matcher.group(5));
                        runCommand(matcher.group(3), 1, arguments);
                    } else if (matcher.group(6) != null) {
                        arguments.add(matcher.group(10));
                        arguments.add(matcher.group(12));
                        runCommand(matcher.group(8), 2, arguments);
                    } else if (matcher.group(13) != null) {
                        arguments.add(matcher.group(17));
                        runCommand(matcher.group(15), 1, arguments);
                    }
                } catch (NoSuchMethodException e) {
                    System.err.println("No such command");
                }
            } else {
                System.err.println("Incorrect command format!");
            }
        }

    }

    private static void runCommand(String command, int argumentNumber, List<String> arguments)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        Class<?> c = Class.forName("com.company.commands.ConsoleCommands");
        if (argumentNumber == 1) {
            String firstArgument = arguments.get(0);
            Method method = c.getDeclaredMethod(command, String.class);
            method.invoke(new ConsoleCommands(), firstArgument);
        } else if (argumentNumber == 2) {
            String firstArgument = arguments.get(0);
            String secondArgument = arguments.get(1);
            Method method = c.getDeclaredMethod(command, String.class, String.class);
            method.invoke(new ConsoleCommands(), firstArgument, secondArgument);
        }
    }
}
