package com.nimaeskandary.controller;

import com.nimaeskandary.model.*;
import com.nimaeskandary.repository.Repository;
import com.nimaeskandary.view.*;
import com.nimaeskandary.view.SurveyActionMenu.SurveyActionMenuSelection;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.util.List;

public class SurveyController {
    private OutputStream outputStream;
    private BufferedReader bufferedReader;
    private Repository repository;
    private AnswerController answerController;
    private Survey currentSurvey;
    private Boolean isTest;

    public SurveyController(OutputStream outputStream, BufferedReader bufferedReader,
                            Repository repository, AnswerController answerController, Boolean isTest) {
        this.outputStream = outputStream;
        this.bufferedReader = bufferedReader;
        this.repository = repository;
        this.answerController = answerController;
        this.currentSurvey = null;
        this.isTest = isTest;
    }

    public void SurveyActionScreen() {
        // display menu
        SurveyActionMenu menu = new SurveyActionMenu(this.bufferedReader, this.outputStream, this.isTest);
        menu.display(menu.getMenuText());
        // get valid selection
        SurveyActionMenuSelection selection = (SurveyActionMenuSelection) menu.getValidMenuSelection();
        switch (selection) {
            case Create:
                this.currentSurvey = this.isTest ? new Test() : new Survey();
                this.addQuestionScreen();
                break;
            case Display:
                this.displayCurrentSurvey();
                break;
            case Load:
                this.loadSurvey();
                break;
            case Save:
                this.saveCurrentSurvey();
                break;
            default:
                break;
        }
    }

    public void displayCurrentSurvey() {
        SimpleInput simpleInput = new SimpleInput(this.bufferedReader, this.outputStream);
        // no survey created or loaded
        if (this.currentSurvey == null) {
            simpleInput.display("No survey or test loaded");
        } else {
            simpleInput.display(this.currentSurvey.toString());
        }
    }

    public void saveCurrentSurvey() {
        // no survey created or loaded
        if (this.currentSurvey == null) {
            SimpleInput simpleInput = new SimpleInput(this.bufferedReader, this.outputStream);
            simpleInput.display("No survey or test loaded");
        } else {
            this.repository.putSurveyRubric(this.currentSurvey, this.isTest);
        }
    }

    public void addQuestionScreen() {
        AddQuestionMenu menu = new AddQuestionMenu(this.bufferedReader, this.outputStream);
        menu.display(menu.getMenuText());
        // Get question type selection
        AddQuestionMenu.AddQuestionMenuSelection selection = (AddQuestionMenu.AddQuestionMenuSelection) menu.getValidMenuSelection();
        switch (selection) {
            case TrueOrFalse:
                this.addTrueOrFalseQuestion();
                break;
            case MultipleChoice:
                this.addMultipleChoiceQuestion();
                break;
            case ShortAnswer:
                this.addShortAnswerQuestion();
                break;
            case Essay:
                this.addEssayQuestion();
                break;
            case Ranking:
                this.addRankingQuestion();
                break;
            case Stop:
                this.nameCurrentSurvey();
                return;
        }
        this.addQuestionScreen();
    }

    public void loadSurvey() {
        // get list of survey/test names
        List<String> options = this.repository.listSurveyNames(this.isTest);
        SimpleInput simpleInput = new SimpleInput(this.bufferedReader, this.outputStream);
        // check that are some
        if (options.size() > 0) {
            simpleInput.display("Options: \n");

            for (String s: options) {
                simpleInput.display(s + "\n");
            }

            // have user select a title that appeared in the list
            simpleInput.display("Input a title: \n");
            String name = simpleInput.getValidInput();
            while (!options.contains(name)) {
                simpleInput.display("Does not exist \n");
                simpleInput.display("Input a title: \n");
                name = simpleInput.getValidInput();
            }

            this.currentSurvey = this.repository.getSurveyRubric(name, this.isTest);
        } else {
            simpleInput.display("None found \n");
        }
    }

    public void nameCurrentSurvey() {
        SimpleInput simpleInput = new SimpleInput(this.bufferedReader, this.outputStream);
        simpleInput.display("Enter a title:\n");
        this.currentSurvey.title = simpleInput.getValidInput();
    }

    public void addTrueOrFalseQuestion() {
        SimpleInput simpleInput = new SimpleInput(this.bufferedReader, this.outputStream);
        simpleInput.display("Enter the prompt for your true/false question:\n");

        String prompt = simpleInput.getValidInput() + "\n";
        PromptList promptList = new PromptList(prompt);

        // if test get right answer
        if(this.isTest) {
            simpleInput.display("What is the correct answer?\n");
            TestQuestion testQuestion = new TestQuestion(SurveyQuestion.QuestionType.TrueOrFalse, prompt);
            testQuestion.correctAnswer = this.answerController.answerQuestion(testQuestion);
            this.currentSurvey.addQuestion(testQuestion);

        } else {
            SurveyQuestion surveyQuestion = new SurveyQuestion(SurveyQuestion.QuestionType.TrueOrFalse, prompt);
            this.currentSurvey.addQuestion(surveyQuestion);
        }
    }

    public void addMultipleChoiceQuestion() {
        SimpleInput simpleInput = new SimpleInput(this.bufferedReader, this.outputStream);
        simpleInput.display("Enter the prompt for your multiple choice question:\n");

        StringBuilder promptText = new StringBuilder(simpleInput.getValidInput());
        promptText.append("\n");

        simpleInput.display("Enter the number of choices for your multiple choice question:\n");
        Integer numChoices = simpleInput.getValidIntegerInput();

        // format prompt: i) input \n
        for (int i = 1; i <= numChoices; i ++) {
            simpleInput.display("Enter choice #" + i + ":\n");
            promptText.append(i);
            promptText.append(") ");
            promptText.append(simpleInput.getValidInput());
            promptText.append("\n");
        }

        PromptList prompt = new PromptList(promptText.toString());

        // if test then get correct answer
        if(this.isTest) {
            simpleInput.display("What is the correct answer?\n");
            TestQuestion testQuestion = new TestQuestion(SurveyQuestion.QuestionType.MultipleChoice, prompt, numChoices);
            testQuestion.correctAnswer = this.answerController.answerQuestion(testQuestion);
            this.currentSurvey.addQuestion(testQuestion);

        } else {
            SurveyQuestion surveyQuestion = new SurveyQuestion(SurveyQuestion.QuestionType.MultipleChoice, prompt, numChoices);
            this.currentSurvey.addQuestion(surveyQuestion);
        }
    }

    public void addShortAnswerQuestion() {
        SimpleInput simpleInput = new SimpleInput(this.bufferedReader, this.outputStream);
        simpleInput.display("Enter the prompt for your short answer question:\n");

        PromptList prompt = new PromptList(simpleInput.getValidInput() + "\n");

        // if test get right answer
        if(this.isTest) {
            simpleInput.display("What is the correct answer?\n");
            TestQuestion testQuestion = new TestQuestion(SurveyQuestion.QuestionType.ShortAnswer, prompt);
            testQuestion.correctAnswer = this.answerController.answerQuestion(testQuestion);
            this.currentSurvey.addQuestion(testQuestion);

        } else {
            SurveyQuestion surveyQuestion = new SurveyQuestion(SurveyQuestion.QuestionType.ShortAnswer, prompt);
            this.currentSurvey.addQuestion(surveyQuestion);
        }
        }

    public void addEssayQuestion() {
        SimpleInput simpleInput = new SimpleInput(this.bufferedReader, this.outputStream);
        simpleInput.display("Enter the prompt for your essay answer question:\n");

        PromptList prompt = new PromptList(simpleInput.getValidInput() + "\n");
        SurveyQuestion surveyQuestion = new SurveyQuestion(SurveyQuestion.QuestionType.EssayAnswer, prompt);

        this.currentSurvey.addQuestion(surveyQuestion);
    }

    public void addRankingQuestion() {
        SimpleInput simpleInput = new SimpleInput(this.bufferedReader, this.outputStream);
        simpleInput.display("Enter the prompt for your ranking question:\n");

        StringBuilder promptText = new StringBuilder(simpleInput.getValidInput());
        promptText.append("\n");

        simpleInput.display("Enter the number of items in your ranking question:\n");
        Integer numChoices = simpleInput.getValidIntegerInput();

        // limit number of choices because chars A-Z are used in logic
        while(numChoices > 27) {
            simpleInput.display("Cannot exceed 27 options\n");
            simpleInput.display("Enter the number of items in your ranking question:\n");
            numChoices = simpleInput.getValidIntegerInput();
        }

        // use chars as counter to use as labels
        for (char option = 'A'; option < 'A' + numChoices; option ++) {
            simpleInput.display("Enter option " + option + " (these are not randomized when outputted):\n");
            promptText.append("Option ");
            promptText.append(option);
            promptText.append(") ");
            promptText.append(simpleInput.getValidInput());
            promptText.append("\n");
        }

        PromptList prompt = new PromptList(promptText.toString());

        // if test get right answer
        if(this.isTest) {
            simpleInput.display("What is the correct answer?\n");
            TestQuestion testQuestion = new TestQuestion(SurveyQuestion.QuestionType.Ranking, prompt, numChoices);
            testQuestion.correctAnswer = this.answerController.answerQuestion(testQuestion);
            this.currentSurvey.addQuestion(testQuestion);

        } else {
            SurveyQuestion surveyQuestion = new SurveyQuestion(SurveyQuestion.QuestionType.Ranking, prompt, numChoices);
            this.currentSurvey.addQuestion(surveyQuestion);
        }
    }
}
