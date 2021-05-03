package service;

import entity.Quiz;
import service.interfaces.DictionaryQuiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SlangWordQuiz implements DictionaryQuiz {
    private final int numSelections;
    private final SlangWordService service;

    interface SelectionsGenerator {
        String generate();
    }

    public SlangWordQuiz(int numSelections, SlangWordService service) {
        this.numSelections = numSelections;
        this.service = service;
    }

    @Override
    public Quiz quizByWord() {
        String word = this.generateSlang();
        var selections = generateSelections(this::generateDefinition);

        Random generator = new Random();
        var idxRightSelection = generator.nextInt(numSelections);
        selections.set(idxRightSelection, service.searchByWord(word).get(0));

        return new Quiz(word,
                this.service.searchByWord(word).get(0),
                selections);
    }

    @Override
    public Quiz quizByDefinition() {
        var slangWord = this.service.randomWord();
        var selections = generateSelections(this::generateSlang);

        Random generator = new Random();
        var idxRightSelection = generator.nextInt(numSelections);
        selections.set(idxRightSelection, slangWord.getWord());

        return new Quiz(slangWord.getDefinitions().get(0),
                slangWord.getWord(),
                selections);
    }

    private String generateSlang() {
        return this.service.randomWord().getWord();
    }

    private String generateDefinition() {
        return this.service.randomDefinition();
    }

    private List<String> generateSelections(SelectionsGenerator generator) {
        var selections = new ArrayList<String>();
        for (var i = 0; i < this.numSelections; i++) {
            String selection = generator.generate();
            while (selections.contains(selection)) {
                selection = generator.generate();
            }
            selections.add(selection);
        }
        return selections;
    }
}
