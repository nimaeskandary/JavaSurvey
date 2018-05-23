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
