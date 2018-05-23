package com.nimaeskandary.controller;

import com.nimaeskandary.model.SurveyQuestion;
import com.nimaeskandary.model.TestQuestion;
import com.nimaeskandary.view.SimpleInput;

import java.io.BufferedReader;
import java.io.OutputStream;

public class EditController {
    private OutputStream outputStream;
    private BufferedReader bufferedReader;
    private AnswerController answerController;

    public EditController(OutputStream outputStream, BufferedReader bufferedReader,
                          AnswerController answerController) {
        this.outputStream = outputStream;
        this.bufferedReader = bufferedReader;
        this.answerController = answerController;
    }

    public SurveyQuestion modifyQuestion(SurveyQuestion question, Boolean isTest) {
        SimpleInput simpleInput = new SimpleInput(this.bufferedReader, this.outputStream);

        question = this.editPrompt(question);

        switch (question.type) {
            case MultipleChoice:
                question = this.editMultipleChoiceOptions(question);
                break;
            case Ranking:
                question = this.editRankingOptions(question);
                break;
            default:
                break;
        }

        if (isTest) {
            simpleInput.display("Do you want to modify the answer? (Y or N):\n");
            String edit = simpleInput.getValidInput();
            // check if Y or N
            while (!(edit.equals("Y") || edit.equals("N"))) {
                simpleInput.display("Input must be Y or N:\n");
                edit = simpleInput.getValidInput();
            }
            if (edit.equals("Y")) {
                ((TestQuestion)question).correctAnswer = this.answerController.answerQuestion(question);
            }
        }
        return question;
    }

    public SurveyQuestion editPrompt(SurveyQuestion question) {
        SimpleInput simpleInput = new SimpleInput(bufferedReader, outputStream);
        simpleInput.display("Do you want to modify the prompt? (Y or N):\n");
        String edit = simpleInput.getValidInput();
        // check if Y or N
        while (!(edit.equals("Y") || edit.equals("N"))) {
            simpleInput.display("Input must be Y or N:\n");
            edit = simpleInput.getValidInput();
        }
        if (edit.equals("Y")) {
            simpleInput.display("Enter a new prompt:\n");
            question.promptList.editPrompt(0, simpleInput.getValidInput());
            simpleInput.display("\nQuestion edited:\n" + question.toString());
        }
        return question;
    }

    public SurveyQuestion editMultipleChoiceOptions(SurveyQuestion question) {
        SimpleInput simpleInput = new SimpleInput(bufferedReader, outputStream);
        simpleInput.display("Do you want to modify an option (Y or N)?");
        String edit = simpleInput.getValidInput();
        // check if Y or N
        while (!(edit.equals("Y") || edit.equals("N"))) {
            simpleInput.display("Input must be Y or N:\n");
            edit = simpleInput.getValidInput();
        }
        if (edit.equals("Y")) {
            simpleInput.display("Select from 1 to " + question.allowedNumChoices + ":\n");
            // get option
            int option = simpleInput.getValidIntegerInput();
            while (option < 1 || option > question.allowedNumChoices) {
                simpleInput.display("Input must be between 1 and " + question.allowedNumChoices + "\n");
                simpleInput.display("Select from 1 to " + question.allowedNumChoices + ":\n");
                option = simpleInput.getValidIntegerInput();
            }
            // edit option
            simpleInput.display(String.format("Enter a new choice for #%d:\n", option));
            question.promptList.editPrompt(option, simpleInput.getValidInput());
            simpleInput.display("\nQuestion edited:\n" + question.toString());
            // check if more should be edited
            return this.editMultipleChoiceOptions(question);
        }
        return question;
    }

    public SurveyQuestion editRankingOptions(SurveyQuestion question) {
        SimpleInput simpleInput = new SimpleInput(bufferedReader, outputStream);
        simpleInput.display("Do you want to modify an option (Y or N)?");
        String edit = simpleInput.getValidInput();
        // check if Y or N
        while (!(edit.equals("Y") || edit.equals("N"))) {
            simpleInput.display("Input must be Y or N:\n");
            edit = simpleInput.getValidInput();
        }
        if (edit.equals("Y")) {
            char lastOption = (char)((int)'A' + question.allowedNumChoices - 1);
            simpleInput.display("Select from A to " + lastOption + ":\n");
            char option = simpleInput.getValidChar();
            while (option < 'A' || option > lastOption) {
                simpleInput.display("Input must be between A and " + lastOption + "\n");
                simpleInput.display("Select from A to " + lastOption + ":\n");
                option = simpleInput.getValidChar();
            }
            // edit option
            simpleInput.display("Enter a new choice for " + option + ":\n");
            int index = option - 'A' + 1;
            question.promptList.editPrompt(index, simpleInput.getValidInput());
            simpleInput.display("\nQuestion edited:\n" + question.toString());
            // check if more should be edited
            return this.editRankingOptions(question);
        }
        return question;
    }
}
