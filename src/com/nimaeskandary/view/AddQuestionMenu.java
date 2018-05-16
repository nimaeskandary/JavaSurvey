package com.nimaeskandary.view;

import java.io.BufferedReader;
import java.io.OutputStream;

public class AddQuestionMenu extends Menu {

    public AddQuestionMenu(BufferedReader bufferedReader, OutputStream outputStream) {
        super(bufferedReader, outputStream);
    }

    public enum AddQuestionMenuSelection implements MenuSelection {
        TrueOrFalse, MultipleChoice, ShortAnswer, Essay, Ranking, Matching, Stop, Invalid;
    }

    @Override
    public MenuSelection stringToMenuSelection(String string) {
        switch (string) {
            case "1":
                return AddQuestionMenuSelection.TrueOrFalse;
            case "2":
                return AddQuestionMenuSelection.MultipleChoice;
            case "3":
                return AddQuestionMenuSelection.ShortAnswer;
            case "4":
                return AddQuestionMenuSelection.Essay;
            case "5":
                return AddQuestionMenuSelection.Ranking;
            case "6":
                return AddQuestionMenuSelection.Matching;
            case "7":
                return AddQuestionMenuSelection.Stop;
            default:
                // make type invalid if others not selected
                return AddQuestionMenuSelection.Invalid;
        }
    }

    @Override
    public Boolean isInputValid(String input) {
        // check that that selection is not "Invalid," only would happen if user did not input a menu item
        return this.stringToMenuSelection(input) != AddQuestionMenuSelection.Invalid;
    }

    @Override
    public String getMenuText() {
        String text = "\n1) Add a new T/F question\n";
        text += "2) Add a new multiple choice question\n";
        text += "3) Add a new short answer question\n";
        text += "4) Add a new essay question\n";
        text += "5) Add a new ranking question\n";
        text += "6) Add a new matching choice question\n";
        text += "7) Stop adding questions\n";
        text += "\nEnter selection:";
        return text;
    }

    @Override
    String invalidInputMessage() {
        return "\nInvalid input! Must be 1, 2, 3, 4, 5, 6, or 7\n" + this.getMenuText();
    }
}
