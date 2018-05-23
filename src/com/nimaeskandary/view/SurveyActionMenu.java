package com.nimaeskandary.view;

import com.nimaeskandary.model.Survey;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;

public class SurveyActionMenu extends Menu {
    private Boolean isTest;

    public SurveyActionMenu(BufferedReader bufferedReader, OutputStream outputStream, Boolean isTest) {
        super(bufferedReader, outputStream);
        this.isTest = isTest;
    }

    public enum SurveyActionMenuSelection implements MenuSelection {
        Create, Display, Load, Save, Modify, Take, Tabulate, MainMenu, Invalid;
    }

    @Override
    public MenuSelection stringToMenuSelection(String string) {
        switch (string) {
            case "1":
                return SurveyActionMenuSelection.Create;
            case "2":
                return SurveyActionMenuSelection.Display;
            case "3":
                return SurveyActionMenuSelection.Load;
            case "4":
                return SurveyActionMenuSelection.Save;
            case "5":
                return SurveyActionMenuSelection.Modify;
            case "6":
                return SurveyActionMenuSelection.Take;
            case "7":
                return SurveyActionMenuSelection.Tabulate;
            case "8":
                return SurveyActionMenuSelection.MainMenu;
            default:
                return SurveyActionMenuSelection.Invalid;
        }
    }

    @Override
    public Boolean isInputValid(String input) {
        return this.stringToMenuSelection(input) != SurveyActionMenuSelection.Invalid;
    }

    @Override
    public String getMenuText() {
        String surveyOrTest = this.isTest ? "Test" : "Survey";
        String text = String.format("\n1) Create a new %s\n", surveyOrTest);
        text += String.format("2) Display a %s\n", surveyOrTest);
        text += String.format("3) Load a %s\n", surveyOrTest);
        text += String.format("4) Save a %s\n", surveyOrTest);
        text += String.format("5) Modify an Existing %s\n", surveyOrTest);
        text += String.format("6) Take a %s\n", surveyOrTest);
        text += String.format("7) Tabulate a %s\n", surveyOrTest);
        text += "8) Main Menu\n";
        text += "\nEnter selection:";
        return text;
    }

    @Override
    String invalidInputMessage() {
        return "\nInvalid input! Must be 1, 2, 3, 4, 5, 6, 7, or 8\n" + this.getMenuText();
    }
}
