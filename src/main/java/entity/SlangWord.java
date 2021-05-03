package entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SlangWord extends DictionaryWord {
    public SlangWord(String word, List<String> definitions) {
        super(word, definitions);
    }

    public static SlangWord parse(String content, String delim) {
        var values = content.split(delim);
        if (0 == values.length) {return null;}
        if (values.length > 1) {
            return new SlangWord(values[0], splitDefinitions(values[1]));
        }
        return new SlangWord(values[0], null);
    }

    private static List<String> splitDefinitions(String content) {
        return Arrays.stream(content.split("\\|")).map(String::trim).collect(Collectors.toList());
    }
}
