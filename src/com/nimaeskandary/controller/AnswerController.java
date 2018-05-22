package com.nimaeskandary.controller;

import com.nimaeskandary.model.*;
import com.nimaeskandary.view.SimpleInput;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class AnswerController {
    private OutputStream outputStream;
    private BufferedReader bufferedReader;

    public AnswerController(OutputStream outputStream, BufferedReader bufferedReader) {
        this.outputStream = outputStream;
        this.bufferedReader = bufferedReader;
    }

    public Answer answerQuestion(SurveyQuestion question) {
        Answer answer = null;
        // essay not required for part 2, matching not implemented
        switch (question.type) {
            case TrueOrFalse:
                answer = this.answerTrueOrFalseQuestion(question);
                break;
            case MultipleChoice:
                answer = this.answerMultipleChoiceQuestion(question, new ArrayList<Integer>(question.allowedNumChoices));
                break;
            case ShortAnswer:
                answer = this.answerShortAnswerQuestion(question, new ArrayList<String>(1));
                break;
            case Ranking:
                answer = this.answerRankingQuestion(question);
                break;
        }

        return answer;
    }

    public Answer answerTrueOrFalseQuestion(SurveyQuestion question) {
        // get answer
        SimpleInput simpleInput = new SimpleInput(bufferedReader, outputStream);
        simpleInput.display("Enter T or F:\n");
        String response = simpleInput.getValidInput();
        // check if T or F
        while(!(response.equals("T") || response.equals("F"))) {
            simpleInput.display("Input must be T or F:");
            response = simpleInput.getValidInput();
        }

        return new TrueOrFalseAnswer(response);
    }

    public Answer answerMultipleChoiceQuestion(SurveyQuestion question, ArrayList<Integer> responses) {
        SimpleInput simpleInput = new SimpleInput(bufferedReader, outputStream);
        simpleInput.display("Select from 1 to " + question.allowedNumChoices + ":\n");

        // get response
        int response = simpleInput.getValidIntegerInput();
        // validate in range
        while(response < 1 || response > question.allowedNumChoices) {
            simpleInput.display("Input must be between 1 and " + question.allowedNumChoices + "\n");
            simpleInput.display("Select from 1 to " + question.allowedNumChoices + ":\n");
            response = simpleInput.getValidIntegerInput();
        }

        responses.add(response);

        // allow user to add more responses until all are selected
        if (responses.size() < question.allowedNumChoices) {
            simpleInput.display("Would you like to add another response? Enter Y or N:\n");
            String another = simpleInput.getValidInput();
            while (!(another.equals("Y") || another.equals("N"))) {
                simpleInput.display("\nInput must be Y or N:\n");
                another = simpleInput.getValidInput();
            }
            if (another.equals("Y")) {
                return this.answerMultipleChoiceQuestion(question, responses);
            }
        }

        return new MultipleChoiceAnswer(responses);
    }

    public Answer answerShortAnswerQuestion(SurveyQuestion question, ArrayList<String> responses) {
        SimpleInput simpleInput = new SimpleInput(bufferedReader, outputStream);
        simpleInput.display("Input a paragraph, [enter] to submit:\n");
        // no extra validations, getValidInput already checks for empty string
        responses.add(simpleInput.getValidInput());

        simpleInput.display("Would you like to add another response? Enter Y or N:\n");
        String another = simpleInput.getValidInput();
        while (!(another.equals("Y") || another.equals("N"))) {
            simpleInput.display("\nInput must be Y or N:\n");
            another = simpleInput.getValidInput();
        }
        if (another.equals("Y")) {
            return this.answerShortAnswerQuestion(question, responses);
        }

        return new ShortAnswerAnswer(responses);
    }

    public Answer answerRankingQuestion(SurveyQuestion question) {
        SimpleInput simpleInput = new SimpleInput(bufferedReader, outputStream);
        HashMap<String, Integer> choices = new HashMap<String, Integer>();

        // start at A and increment letter for each choice
        for (char option = 'A'; option < 'A' + question.allowedNumChoices; option++) {
            // get input
            simpleInput.display("Input rank for option " + option + ", must be between 1 and " + question.allowedNumChoices + " :");
            Integer input = simpleInput.getValidIntegerInput();
            // validate in range
            while(input < 1 || input > question.allowedNumChoices) {
                simpleInput.display("Rank must be between 1 and " + question.allowedNumChoices + "\n");
                simpleInput.display("Input rank for option " + option + ":");
                input = simpleInput.getValidIntegerInput();
            }
            choices.put(Character.toString(option), input);
        }

        return new RankingAnswer(choices);
    }
}
