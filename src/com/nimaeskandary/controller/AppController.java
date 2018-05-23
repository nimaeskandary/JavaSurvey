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
    private EditController editController;

    public AppController(InputStream inputStream, OutputStream outputStream, Repository repository) {
        this.outputStream = outputStream;
        this.repository = repository;
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        this.answerController = new AnswerController(outputStream, this.bufferedReader);
        this.editController = new EditController(outputStream, this.bufferedReader, this.answerController);
        this.surveyController = new SurveyController(outputStream, this.bufferedReader, repository, this.answerController,
                this.editController, false);
        this.testController = new SurveyController(outputStream, this.bufferedReader, repository, this.answerController,
                this.editController, true);
        }

    public void menuScreen() {
        StartMenu menu = new StartMenu(bufferedReader, outputStream);
        menu.display(menu.getMenuText());
        StartMenu.StartMenuSelection selection = (StartMenu.StartMenuSelection) menu.getValidMenuSelection();
        switch (selection) {
            case Survey:
                this.surveyController.SurveyActionScreen();
                break;
            case Test:
                this.testController.SurveyActionScreen();
                break;
            default:
                return;
        }
        this.menuScreen();
    }
}
