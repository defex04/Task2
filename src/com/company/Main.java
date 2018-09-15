package com.company;

import com.company.commands.ConsoleCommands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        String test1 = "ls -l";
        String test2 = "cat  filename.txt";
        String test3 = "move file1.txt file2.txt";

        String commandFormat = "([a-zA-Z][a-zA-Z0-9]*)";
        String argumentFormat = "([a-zA-Z0-9]*[\\.]?[a-zA-Z0-9]*)";
        String keyFormat = "([\\-][a-zA-Z0-9]*)";

        String oneArgumentCommand = "((^" + commandFormat + "(\\s+)" + argumentFormat + "$))";
        String twoArgumentCommand = "((^" + commandFormat + "(\\s+)" + argumentFormat + "(\\s+)"
                + argumentFormat + "$))";
        String commandWithKey = "((^" + commandFormat + "(\\s+)" + keyFormat + "$))";

        String expression = oneArgumentCommand + "|" + twoArgumentCommand + "|" + commandWithKey;
        Pattern expressionPattern = Pattern.compile(expression);
        Matcher matcher = expressionPattern.matcher(test2);

        List<String> arguments = new ArrayList<>();


        if (matcher.find()) {

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

        } else {
            System.out.println("Incorrect command format!");
        }

    }

    public static void runCommand(String command, int argumentNumber, List<String> arguments)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        Class<?> c = Class.forName("com.company.commands.ConsoleCommands");
        if (argumentNumber == 1) {
            String firstArgument = arguments.get(0);
            Method method = c.getDeclaredMethod(command, String.class);
            method.invoke(new ConsoleCommands(), firstArgument);
        }
        else if (argumentNumber == 2) {
            String firstArgument = arguments.get(0);
            String secondArgument = arguments.get(1);
            Method method = c.getDeclaredMethod(command, String.class, String.class);
            method.invoke(new ConsoleCommands(), firstArgument, secondArgument);
        }
    }
}
