package entity;

import java.util.List;

public abstract class DictionaryWord {
    private String word;
    private List<String> definitions;
    public static DictionaryWord NOWORD;

    public DictionaryWord() {}

    public DictionaryWord(String word, List<String> definitions) {
        this.word = word;
        this.definitions = definitions;
    }

    public String getWord() {
        return word;
    }

    public List<String> getDefinitions() {
        return definitions;
    }

    public void addDefinition(String definition) {
        definitions.add(definition);
    }
}
