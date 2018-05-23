package com.nimaeskandary.model;

import java.io.Serializable;

public class Test extends Survey implements Serializable {
    public Test() {
        super();
    }

    public Test(Survey survey) {
        super(survey);
    }
}
