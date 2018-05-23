package com.nimaeskandary.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

public class SimpleInput {
    private BufferedReader bufferedReader;
    private OutputStream outputStream;

    public SimpleInput(BufferedReader bufferedReader, OutputStream outputStream) {
        this.bufferedReader = bufferedReader;
        this.outputStream = outputStream;
    }

    public void display(String displayText) {
        try {
            this.outputStream.write(displayText.getBytes());
            this.outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getValidInput() {
        // get a non empty string from user
        String input = "";
        try {
            input = bufferedReader.readLine();
            while(input.isEmpty()) {
                this.outputStream.write("Input cannot be empty:".getBytes());
                this.outputStream.flush();
                input = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return input;
    }

    public Integer getValidIntegerInput() {
        // get an integer from user
        Integer input = 0;
        try {
            String stringInput = bufferedReader.readLine();

            while (!isInteger(stringInput)) {
                this.outputStream.write("Input must be an integer:".getBytes());
                this.outputStream.flush();
                stringInput = bufferedReader.readLine();
            }

            input = Integer.parseInt(stringInput);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return input;
    }

    public char getValidChar() {
        String input = "";
        try {
            input = bufferedReader.readLine();
            while(input.isEmpty() || input.length() > 1) {
                this.outputStream.write("Input must have length of 1:".getBytes());
                this.outputStream.flush();
                input = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return input.toCharArray()[0];
    }

    // validate inputted string is only comprised of integers
    private Boolean isInteger(String input) {
        char[] chars = input.toCharArray();
        for (char c: chars) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
