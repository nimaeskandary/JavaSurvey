package com.nimaeskandary.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PromptList implements Serializable {
    private List<String> prompts;

    public PromptList() {
        this.prompts = new ArrayList<String>(1);
    }

    public PromptList(String firstPrompt) {
        this.prompts = new ArrayList<String>(1);
        this.prompts.add(firstPrompt);
    }

    public void addPrompt(String prompt) {
        this.prompts.add(prompt);
    }

    public List<String> getPrompts() {
        return this.prompts;
    }

    public void editPrompt(int index, String replacement) {
        this.prompts.remove(index);
        this.prompts.add(index, replacement);
    }

    PromptList(PromptList promptList) {
        prompts = promptList.prompts;
    }

    @Override
    public String toString() {
        String out = "";
        for (String prompt: this.prompts) {
            out += prompt;
            out += "\n";
        }
        return out;
    }
}
