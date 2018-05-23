package com.nimaeskandary.view;

import java.io.BufferedReader;
import java.io.OutputStream;

public class TestActionMenu extends SurveyActionMenu {
    public TestActionMenu(BufferedReader bufferedReader, OutputStream outputStream, Boolean isTest) {
        super(bufferedReader, outputStream, isTest);
    }

    @Override
    public MenuSelection stringToMenuSelection(String string) {
        switch (string) {
            case "1":
                return SurveyActionMenu.SurveyActionMenuSelection.Create;
            case "2":
                return SurveyActionMenu.SurveyActionMenuSelection.Display;
            case "3":
                return SurveyActionMenu.SurveyActionMenuSelection.Load;
            case "4":
                return SurveyActionMenu.SurveyActionMenuSelection.Save;
            case "5":
                return SurveyActionMenu.SurveyActionMenuSelection.Modify;
            case "6":
                return SurveyActionMenu.SurveyActionMenuSelection.Take;
            case "7":
                return SurveyActionMenu.SurveyActionMenuSelection.Tabulate;
            case "8":
                return SurveyActionMenu.SurveyActionMenuSelection.Grade;
            case "9":
                return SurveyActionMenu.SurveyActionMenuSelection.MainMenu;
            default:
                return SurveyActionMenu.SurveyActionMenuSelection.Invalid;
        }
    }

    @Override
    public Boolean isInputValid(String input) {
        return this.stringToMenuSelection(input) != SurveyActionMenu.SurveyActionMenuSelection.Invalid;
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
        text += String.format("8) Grade a %s\n", surveyOrTest);
        text += "9) Main Menu\n";
        text += "\nEnter selection:";
        return text;
    }

    @Override
    String invalidInputMessage() {
        return "\nInvalid input! Must be 1, 2, 3, 4, 5, 6, 7, 8, or 9\n" + this.getMenuText();
    }
}
