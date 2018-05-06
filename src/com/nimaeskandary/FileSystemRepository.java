package com.nimaeskandary;

public class FileSystemRepository extends Repository {
    private String root;

    public FileSystemRepository(String root) {
        this.root = root;
    }

    @Override
    public void putSurveyRubric(Survey survey) {

    }

    @Override
    public Survey getSurveyRubric(String surveyName) {
        return null;
    }

    @Override
    public void putUserSurvey(Survey survey) {

    }

    @Override
    public Survey getUserSurvey(String userName) {
        return null;
    }
}
