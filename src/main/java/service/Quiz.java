package service;

import java.util.List;

public class Quiz {
    private String question;
    private String answer;
    private List<String> selections;

    public Quiz(String question, String answer, List<String> selections) {
        this.question = question;
        this.answer = answer;
        this.selections = selections;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getSelections() {
        return selections;
    }

    public boolean isRightSelection(int selectionIndex) {
        return answer == selections.get(selectionIndex);
    }
}
