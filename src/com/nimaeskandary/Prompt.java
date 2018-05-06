package com.nimaeskandary;

public class Prompt {
    private String promptMessage;

    public Prompt(String promptMessage) {
        this.promptMessage = promptMessage;
    }

    Prompt(Prompt prompt) {
        promptMessage = prompt.promptMessage;
    }
}
