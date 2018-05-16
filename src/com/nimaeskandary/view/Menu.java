package com.nimaeskandary.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

abstract public class Menu {
    private BufferedReader bufferedReader;
    private OutputStream outputStream;

    public Menu(BufferedReader bufferedReader, OutputStream outputStream) {
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

    public MenuSelection getValidMenuSelection() {
        String input = "";
        try {
            input = bufferedReader.readLine();
            // check that user inputted a valid option
            while (!this.isInputValid(input)) {
                this.display(this.invalidInputMessage());
                input = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.stringToMenuSelection(input);
    }

    abstract public MenuSelection stringToMenuSelection(String string);

    abstract Boolean isInputValid(String input);

    abstract String getMenuText();

    abstract String invalidInputMessage();
}
