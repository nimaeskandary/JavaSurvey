package com.nimaeskandary;

public class Test extends Survey {
    public Test(String title, String takerName) {
        super(title, takerName);
    }

    public Test(Survey survey) {
        super(survey);
    }

    public double gradeTest() {
        return 0.0;
    }
}
