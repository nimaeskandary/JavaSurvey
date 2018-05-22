package com.nimaeskandary.model;

import java.util.ArrayList;

public class MultipleChoicePromptList extends PromptList {
    public MultipleChoicePromptList(String firstPrompt) {
        super(firstPrompt);
    }

    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < this.prompts.size(); i++) {
            if (i != 0) {
                // print option number
                out += i + ") ";
            }
            out += this.prompts.get(0);
            out += "\n";
        }
        return out;
    }
}
