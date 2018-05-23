package com.nimaeskandary.repository;

import com.nimaeskandary.model.Survey;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileSystemRepository extends Repository {
    private String root;

    public FileSystemRepository(String root) {
        this.root = root;
    }

    @Override
    public void putSurveyRubric(Survey survey, Boolean isTest) {
        String path = isTest ? "tests" : "surveys";
        try {
            // make directory if it doesn't exist
            new File(String.format("%s/%s", this.root, path)).mkdirs();
            // serialize then write to file
            FileOutputStream fileOutputStream = new FileOutputStream(String.format("%s/%s/%s",
                    this.root, path, survey.title));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(survey);
            objectOutputStream.close(); // closes fileOutputStream too
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> listSurveyNames(Boolean isTest) {
        String path = isTest ? "/tests" : "/surveys";
        // get directory holding surveys
        File directory = new File(this.root + path);
        // list files in directory
        File[] surveys = directory.listFiles();
        if (surveys != null) {
            List<String> names = new ArrayList<String>(surveys.length);
            // collect file names
            for (int i = 0; i < surveys.length; i++) {
                names.add(surveys[i].getName());
            }
            // sort in case future naming scheme is surveyname-takername
            Collections.sort(names);
            return names;
        } else {
            return new ArrayList<String>();
        }
    }

    @Override
    public Survey getSurvey(String surveyName, Boolean isTest) {
        String path = isTest ? "tests" : "surveys";
        Survey survey = null;
        try {
            // read serialized object from file
            FileInputStream fileInputStream = new FileInputStream(String.format("%s/%s/%s",
                    this.root, path, surveyName, surveyName));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            survey = (Survey) objectInputStream.readObject();
            objectInputStream.close(); // closes fileInputStream too
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return survey;
    }

    @Override
    public void putUserSurvey(Survey survey, Boolean isTest) {
        String path = isTest ? "tests" : "surveys";
        try {
            // make directory if it doesn't exist
            new File(String.format("%s/%s", this.root, path)).mkdirs();
            // serialize then write to file
            FileOutputStream fileOutputStream = new FileOutputStream(String.format("%s/%s/%s-%s",
                    this.root, path, survey.title, survey.takerName));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(survey);
            objectOutputStream.close(); // closes fileOutputStream too
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
