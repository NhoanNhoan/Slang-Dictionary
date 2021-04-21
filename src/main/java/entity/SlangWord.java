package entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlangWord extends DictionaryWord {
    public SlangWord() {
        super();
    }
    public SlangWord(String word, List<String> definitions) {
        super(word, definitions);
    }

    public static SlangWord parse(String content, String delim) {
        var values = content.split(delim);
        if (0 == values.length) {return null;}

        var definitions = new ArrayList<String>(values.length - 1);
        for (var i = 1; i < values.length; i++) {
            definitions.set(i - 1, values[i]);
        }

        return new SlangWord(values[0], definitions);
    }
}
