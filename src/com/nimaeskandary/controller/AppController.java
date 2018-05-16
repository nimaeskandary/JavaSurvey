package com.nimaeskandary.controller;

import com.nimaeskandary.repository.Repository;
import com.nimaeskandary.view.StartMenu;

import java.io.*;

public class AppController {
    private OutputStream outputStream;
    private BufferedReader bufferedReader;
    private Repository repository;
    private SurveyController surveyController;
    private SurveyController testController;
    private AnswerController answerController;

    public AppController(InputStream inputStream, OutputStream outputStream, Repository repository) {
        this.outputStream = outputStream;
        this.repository = repository;
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        this.answerController = new AnswerController(outputStream, bufferedReader);
        this.surveyController = new SurveyController(outputStream, bufferedReader, repository, this.answerController, false);
        this.testController = new SurveyController(outputStream, bufferedReader, repository, this.answerController, true);
    }

    public void menuScreen() {
        StartMenu menu = new StartMenu(bufferedReader, outputStream);
        menu.display(menu.getMenuText());
        StartMenu.StartMenuSelection selection = (StartMenu.StartMenuSelection) menu.getValidMenuSelection();
        switch (selection) {
            case Survey:
                this.surveyController.SurveyActionScreen();
                break;
            default:
                this.testController.SurveyActionScreen();
                break;
        }
        this.menuScreen();
    }
}
