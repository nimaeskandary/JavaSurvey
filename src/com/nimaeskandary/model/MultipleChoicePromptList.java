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
            out += this.prompts.get(i);
            out += "\n";
        }
        return out;
    }

    @Override
    public boolean equals(Object o) {
        PromptList other = (PromptList) o;
        return this.prompts.equals(other.prompts);
    }

    @Override
    public int hashCode() {
        return this.prompts.hashCode();
    }
}
