package com.nimaeskandary.model;

import java.util.HashMap;
import java.util.List;

public class SurveyResults {
    public HashMap<PromptList, List<Answer>> resultsMap;

    public SurveyResults() {
        this.resultsMap = new HashMap<PromptList, List<Answer>>();
    }
}
