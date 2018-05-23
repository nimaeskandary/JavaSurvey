package com.nimaeskandary.controller;

import com.nimaeskandary.model.*;
import com.nimaeskandary.repository.Repository;
import com.nimaeskandary.view.*;
import com.nimaeskandary.view.SurveyActionMenu.SurveyActionMenuSelection;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SurveyController {
    private OutputStream outputStream;
    private BufferedReader bufferedReader;
    private Repository repository;
    private AnswerController answerController;
    private Survey currentSurvey;
    private EditController editController;
    private Boolean isTest;

    public SurveyController(OutputStream outputStream, BufferedReader bufferedReader,
                            Repository repository, AnswerController answerController,
                            EditController editController, Boolean isTest) {
        this.outputStream = outputStream;
        this.bufferedReader = bufferedReader;
        this.repository = repository;
        this.answerController = answerController;
        this.currentSurvey = null;
        this.editController = editController;
        this.isTest = isTest;
    }

    public void SurveyActionScreen() {
        // display menu
        SurveyActionMenu menu;
        if (this.isTest) {
            menu = new TestActionMenu(this.bufferedReader, this.outputStream, this.isTest);
        } else {
            menu = new SurveyActionMenu(this.bufferedReader, this.outputStream, this.isTest);
        }
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
            case Modify:
                this.chooseSurveyToModify();
                break;
            case Take:
                this.chooseSurveyToTake();
                break;
            case Tabulate:
                this.chooseSurveyToTabulate();
                break;
            case Grade:
                this.chooseSurveyToGrade();
            default:
                break;
        }
    }

    public void takeSurvey() {
        SimpleInput simpleInput = new SimpleInput(this.bufferedReader, this.outputStream);
        simpleInput.display("Enter your name:\n");
        this.currentSurvey.takerName = simpleInput.getValidInput();
        for (SurveyQuestion q: this.currentSurvey.questions) {
            simpleInput.display(q.promptList.toString());
            q.userAnswer = this.answerController.answerQuestion(q);
        }
        this.repository.putUserSurvey(this.currentSurvey, this.isTest);
    }

    public void modifySurvey() {
        SimpleInput simpleInput = new SimpleInput(this.bufferedReader, this.outputStream);
        simpleInput.display("\nCurrent:\n\n");
        this.displayCurrentSurvey();

        simpleInput.display("Do you want to modify this? (Y or N):\n");
        String edit = simpleInput.getValidInput();
        // check if T or F
        while (!(edit.equals("Y") || edit.equals("N"))) {
            simpleInput.display("Input must be Y or N:\n");
            edit = simpleInput.getValidInput();
        }

        if (this.currentSurvey.questions.size() > 0) {
            if (edit.equals("Y")) {
                simpleInput.display(String.format("Choose a question to modify (input 1 to %d):\n", this.currentSurvey.questions.size()));
                int modifyIndex = simpleInput.getValidIntegerInput();
                while (modifyIndex < 1 || modifyIndex > this.currentSurvey.questions.size()) {
                    simpleInput.display("Error: input must be a number in the range [1,6]:\n");
                    modifyIndex = simpleInput.getValidIntegerInput();
                }
                // offset by one to be used as array index
                modifyIndex = modifyIndex - 1;
                this.editController.modifyQuestion(this.currentSurvey.questions.get(modifyIndex), this.isTest);
                this.modifySurvey();
            } else {
                simpleInput.display("Do you want to save this and overwrite the original? (Y or N):\n");
                edit = simpleInput.getValidInput();
                // check if T or F
                while (!(edit.equals("Y") || edit.equals("N"))) {
                    simpleInput.display("Input must be Y or N:\n");
                    edit = simpleInput.getValidInput();
                }
                if (edit.equals("Y")) {
                    this.saveCurrentSurvey();
                }
            }
        } else {
            simpleInput.display("\nError: no questions\n");
        }
    }

    public void tabulateSurvey() {
        // get list of survey/test names
        List<String> allOptions = this.repository.listSurveyNames(this.isTest);
        List<String> options = new ArrayList<String>(1);
        for (String name: allOptions) {
            // check its one of the desired surveys
            if (name.startsWith(String.format("%s-", this.currentSurvey.title))) {
                options.add(name);
            }
        }

        // collect completed surveys
        List<Survey> surveys = new ArrayList<Survey>(options.size());
        for (String name: options) {
            surveys.add(this.repository.getSurvey(name, this.isTest));
        }

        // collect answers for each prompt
        SurveyResults results = new SurveyResults();
        for (Survey s: surveys) {
            for (SurveyQuestion q: s.questions) {
                // check if prompt exists already in map
                if (results.resultsMap.containsKey(q.promptList)) {
                    List<Answer> answers = results.resultsMap.get(q.promptList);
                    answers.add(q.userAnswer);
                    results.resultsMap.put(q.promptList, answers);
                } else {
                    List<Answer> answers = new ArrayList<Answer>(1);
                    answers.add(q.userAnswer);
                    results.resultsMap.put(q.promptList, answers);
                }
            }
        }
        SimpleInput simpleInput = new SimpleInput(this.bufferedReader, this.outputStream);
        for (PromptList key: results.resultsMap.keySet()) {
            Set<Answer> answerSet = new HashSet<Answer>(results.resultsMap.get(key));
            simpleInput.display("prompt:\n");
            simpleInput.display(key.toString());
            for (Answer answer: answerSet) {
                simpleInput.display("\nanswer:\n");
                simpleInput.display(answer.toString());
                int count = 0;
                for (int i = 0; i < results.resultsMap.get(key).size(); i++) {
                    if (results.resultsMap.get(key).get(i).equals(answer)) {
                        count++;
                    }
                }
                simpleInput.display(String.format("\noccurred %d times\n\n", count));
            }
        }
    }

    public void gradeSurvey() {
        int numCorrect = 0;
        for (SurveyQuestion sq: this.currentSurvey.questions) {
            if (!sq.type.equals(SurveyQuestion.QuestionType.EssayAnswer)) {
                TestQuestion q = (TestQuestion) sq;
                if (q.correctAnswer.equals(q.userAnswer)) {
                    numCorrect++;
                }
            }
        }

        SimpleInput simpleInput = new SimpleInput(this.bufferedReader, this.outputStream);
        double grade = (double) numCorrect / this.currentSurvey.questions.size() * 100;
        simpleInput.display(String.format("Grade: %.2f\n", grade));
    }

    public void chooseSurveyToModify() {
        SimpleInput simpleInput = new SimpleInput(this.bufferedReader, this.outputStream);
        simpleInput.display("\nChoose one to modify\n");
        this.loadSurvey();
        this.modifySurvey();
    }

    public void chooseSurveyToTake() {
        SimpleInput simpleInput = new SimpleInput(this.bufferedReader, this.outputStream);
        simpleInput.display("\nChoose one to take\n");
        this.loadSurvey();
        this.takeSurvey();
    }

    public void chooseSurveyToGrade() {
        SimpleInput simpleInput = new SimpleInput(this.bufferedReader, this.outputStream);
        simpleInput.display("\nChoose one to grade\n");
        this.loadSurvey();
        this.gradeSurvey();
    }

    public void chooseSurveyToTabulate() {
        // get list of survey/test names
        List<String> options = this.repository.listSurveyNames(this.isTest);
        SimpleInput simpleInput = new SimpleInput(this.bufferedReader, this.outputStream);
        // check that are some
        if (options.size() > 0) {
            simpleInput.display("\nChoose one to tabulate\n");
            simpleInput.display("Options: \n");

            for (String s: options) {
                // only show rubrics
                if(!s.contains("-")) {
                    simpleInput.display(s + "\n");
                }
            }

            // have user select a title that appeared in the list
            simpleInput.display("Input a title: \n");
            String name = simpleInput.getValidInput();
            if (!options.contains(name)) {
                simpleInput.display("\nError: does not exist!\n\n");
                this.chooseSurveyToTabulate();
                return;
            }

            this.currentSurvey = this.repository.getSurvey(name, this.isTest);
            this.tabulateSurvey();
        } else {
            simpleInput.display("None found \n");
        }
    }

    public void displayCurrentSurvey() {
        SimpleInput simpleInput = new SimpleInput(this.bufferedReader, this.outputStream);
        // no survey created or loaded
        if (this.currentSurvey == null) {
            simpleInput.display("No survey or test loaded\n");
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
            case Matching:
                this.addMatchingQuestion();
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
            if (!options.contains(name)) {
                simpleInput.display("\nError: does not exist!\n\n");
                this.loadSurvey();
                return;
            }

            this.currentSurvey = this.repository.getSurvey(name, this.isTest);
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

        PromptList promptList = new PromptList(simpleInput.getValidInput());

        // if test get right answer
        if(this.isTest) {
            simpleInput.display("What is the correct answer?\n");
            TestQuestion testQuestion = new TestQuestion(SurveyQuestion.QuestionType.TrueOrFalse, promptList);
            testQuestion.correctAnswer = this.answerController.answerQuestion(testQuestion);
            this.currentSurvey.addQuestion(testQuestion);

        } else {
            SurveyQuestion surveyQuestion = new SurveyQuestion(SurveyQuestion.QuestionType.TrueOrFalse, promptList);
            this.currentSurvey.addQuestion(surveyQuestion);
        }
    }

    public void addMultipleChoiceQuestion() {
        SimpleInput simpleInput = new SimpleInput(this.bufferedReader, this.outputStream);
        simpleInput.display("Enter the prompt for your multiple choice question:\n");

        MultipleChoicePromptList promptList = new MultipleChoicePromptList(simpleInput.getValidInput());

        simpleInput.display("Enter the number of choices for your multiple choice question:\n");
        Integer numChoices = simpleInput.getValidIntegerInput();

        // add options
        for (int i = 1; i <= numChoices; i ++) {
            simpleInput.display("Enter choice #" + i + ":\n");
            promptList.addPrompt(simpleInput.getValidInput());
        }

        // if test then get correct answer
        if(this.isTest) {
            simpleInput.display("What is the correct answer?\n");
            TestQuestion testQuestion = new TestQuestion(SurveyQuestion.QuestionType.MultipleChoice, promptList, numChoices);
            testQuestion.correctAnswer = this.answerController.answerQuestion(testQuestion);
            this.currentSurvey.addQuestion(testQuestion);

        } else {
            SurveyQuestion surveyQuestion = new SurveyQuestion(SurveyQuestion.QuestionType.MultipleChoice, promptList, numChoices);
            this.currentSurvey.addQuestion(surveyQuestion);
        }
    }

    public void addShortAnswerQuestion() {
        SimpleInput simpleInput = new SimpleInput(this.bufferedReader, this.outputStream);
        simpleInput.display("Enter the prompt for your short answer question:\n");

        PromptList promptList = new PromptList(simpleInput.getValidInput());

        // if test get right answer
        if(this.isTest) {
            simpleInput.display("What is the correct answer?\n");
            TestQuestion testQuestion = new TestQuestion(SurveyQuestion.QuestionType.ShortAnswer, promptList);
            testQuestion.correctAnswer = this.answerController.answerQuestion(testQuestion);
            this.currentSurvey.addQuestion(testQuestion);

        } else {
            SurveyQuestion surveyQuestion = new SurveyQuestion(SurveyQuestion.QuestionType.ShortAnswer, promptList);
            this.currentSurvey.addQuestion(surveyQuestion);
        }
        }

    public void addEssayQuestion() {
        SimpleInput simpleInput = new SimpleInput(this.bufferedReader, this.outputStream);
        simpleInput.display("Enter the prompt for your essay answer question:\n");

        PromptList promptList = new PromptList(simpleInput.getValidInput());
        SurveyQuestion surveyQuestion = new SurveyQuestion(SurveyQuestion.QuestionType.EssayAnswer, promptList);

        this.currentSurvey.addQuestion(surveyQuestion);
    }

    public void addRankingQuestion() {
        SimpleInput simpleInput = new SimpleInput(this.bufferedReader, this.outputStream);
        simpleInput.display("Enter the prompt for your ranking question:\n");

        RankingPromptList promptList = new RankingPromptList(simpleInput.getValidInput());

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
            promptList.addPrompt(simpleInput.getValidInput());
        }

        // if test get right answer
        if(this.isTest) {
            simpleInput.display("What is the correct answer?\n");
            TestQuestion testQuestion = new TestQuestion(SurveyQuestion.QuestionType.Ranking, promptList, numChoices);
            testQuestion.correctAnswer = this.answerController.answerQuestion(testQuestion);
            this.currentSurvey.addQuestion(testQuestion);

        } else {
            SurveyQuestion surveyQuestion = new SurveyQuestion(SurveyQuestion.QuestionType.Ranking, promptList, numChoices);
            this.currentSurvey.addQuestion(surveyQuestion);
        }
    }

    public void addMatchingQuestion() {
        SimpleInput simpleInput = new SimpleInput(this.bufferedReader, this.outputStream);
        simpleInput.display("Enter the prompt for your matching question:\n");

        MatchingPromptList promptList = new MatchingPromptList(simpleInput.getValidInput());
        simpleInput.display("Enter the number of pairs in your matching question:\n");
        Integer numChoices = simpleInput.getValidIntegerInput();

        // limit number of choices because chars A-Z are used in logic
        while(numChoices > 27) {
            simpleInput.display("Cannot exceed 27 pairs\n");
            simpleInput.display("Enter the number of pairs in your matching question:\n");
            numChoices = simpleInput.getValidIntegerInput();
        }

        // enter group one values
        for (char option = 'A'; option < 'A' + numChoices; option ++) {
            simpleInput.display("Enter option " + option + " (category one):\n");
            promptList.addPrompt(simpleInput.getValidInput());
        }

        // enter group two values
        for (int i = 1; i <= numChoices; i ++) {
            simpleInput.display("Enter option " + i + " (category two):\n");
            promptList.addPrompt(simpleInput.getValidInput());
        }

        // if test get right answer
        if(this.isTest) {
            simpleInput.display("What is the correct answer?\n");
            TestQuestion testQuestion = new TestQuestion(SurveyQuestion.QuestionType.Matching, promptList, numChoices);
            testQuestion.correctAnswer = this.answerController.answerQuestion(testQuestion);
            this.currentSurvey.addQuestion(testQuestion);

        } else {
            SurveyQuestion surveyQuestion = new SurveyQuestion(SurveyQuestion.QuestionType.Matching, promptList, numChoices);
            this.currentSurvey.addQuestion(surveyQuestion);
        }
    }
}
