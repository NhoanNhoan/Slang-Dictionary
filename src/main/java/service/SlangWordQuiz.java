package service;

import entity.SlangWord;
import service.interfaces.DictionaryQuiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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
        return new Quiz(word,
                this.service.search(word).get(0),
                generateSelections(this::generateDefinition));
    }

    @Override
    public Quiz quizByDefinition() {
        var slangWord = this.service.randomWord();
        return new Quiz(slangWord.getDefinitions().get(0),
                slangWord.getWord(),
                this.generateSelections(this::generateSlang));
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
            String selection = null;
            while (!selections.contains(selection = generator.generate())) {
                selection = selection = generator.generate();
            }
            selections.add(generator.generate());
        }
        return selections;
    }
}
