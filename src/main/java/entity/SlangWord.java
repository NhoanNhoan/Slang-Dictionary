package entity;

public class SlangWord extends DictionaryWord {
    public SlangWord() {
        super();
    }
    public SlangWord(String definition, String value) {
        super(definition, value);
    }

    public static SlangWord makeByString(String content, String delim) {
        var values = content.split(delim);
        return new SlangWord(values[0], values[1]);
    }
}
