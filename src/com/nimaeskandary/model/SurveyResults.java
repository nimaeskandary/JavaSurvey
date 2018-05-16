package com.nimaeskandary.model;

import java.util.HashMap;

public class SurveyResults {
    public HashMap<PromptList, Answer> resultsMap;

    public SurveyResults() {
        this.resultsMap = new HashMap<PromptList, Answer>();
    }
}
