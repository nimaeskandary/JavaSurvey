package com.nimaeskandary;

import java.util.HashMap;

public class SurveyResults {
    public HashMap<Prompt, Answer> resultsMap;

    public SurveyResults() {
        this.resultsMap = new HashMap<Prompt, Answer>();
    }
}
