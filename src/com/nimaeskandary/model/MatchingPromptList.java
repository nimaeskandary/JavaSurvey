package com.nimaeskandary.model;

public class MatchingPromptList extends PromptList {
    public MatchingPromptList(String firstPrompt) {
        super(firstPrompt);
    }

    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < this.prompts.size(); i++) {
            int groupOneEnd = (this.prompts.size()) / 2;
            if (i != 0 && i <= groupOneEnd) {
                // print group one labels
                char label = (char)((int)'A' + i - 1);
                out += label;
                out += ") ";
            }

            if (i > groupOneEnd) {
                // print group two labels
                out += i - groupOneEnd;
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
