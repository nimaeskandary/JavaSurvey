package com.nimaeskandary;

import java.io.*;

public class Client {
    private InputStream inputStream;
    private OutputStream outputStream;
    private BufferedReader bufferedReader;
    private Repository repository;

    public Client(InputStream inputStream, OutputStream outputStream, Repository repository) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.repository = repository;
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public void start() {

    }

    public void display(String message) {

    }

    public void getUserInput() {

    }

    public void createSurvey(String surveyName) {

    }

    public void takeSurvey(String surveyName, String userName) {

    }

    public void tabulateSurveyResults(String surveyName) {

    }

    public void exit() {

    }
}
