package com.nimaeskandary.view;

import java.io.BufferedReader;
import java.io.OutputStream;

public class StartMenu extends Menu {

    public StartMenu(BufferedReader bufferedReader, OutputStream outputStream) {
        super(bufferedReader, outputStream);
    }

    public enum StartMenuSelection implements MenuSelection {
        Survey, Test, Quit, Invalid;
    }

    @Override
    public MenuSelection stringToMenuSelection(String string) {
        switch (string) {
            case "1":
                return StartMenuSelection.Survey;
            case "2":
                return StartMenuSelection.Test;
            case "3":
                return StartMenuSelection.Quit;
            default:
                return StartMenuSelection.Invalid;
        }
    }

    @Override
    public Boolean isInputValid(String input) {
        return this.stringToMenuSelection(input) != StartMenuSelection.Invalid;
    }

    @Override
    public String getMenuText() {
        String text = "\n1) Survey\n";
        text += "2) Test\n";
        text += "3) Quit\n";
        text += "\nEnter selection:";
        return text;
    }

    @Override
    String invalidInputMessage() {
        return "\nInvalid input! Must be 1, 2, or 3\n" + this.getMenuText();
    }
}
