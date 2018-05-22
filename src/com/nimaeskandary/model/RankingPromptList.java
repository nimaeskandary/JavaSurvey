package com.nimaeskandary.model;

public class RankingPromptList extends PromptList {
    public RankingPromptList(String firstPrompt) {
        super(firstPrompt);
    }

    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < this.prompts.size(); i++) {
            if (i != 0) {
                // print option number
                char label = (char)((int)'A' + i - 1);
                out += label;
                out += ") ";
            }
            out += this.prompts.get(0);
            out += "\n";
        }
        return out;
    }
}
