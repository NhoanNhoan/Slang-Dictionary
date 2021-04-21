import repo.textfile.SlangTextRepo;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] argv)
    {
       HashMap<Character, Object> values = new HashMap<>();
        String content = "abc";
        String data = "XYZ";
        var curMap = values;

        for (var i = 0; i < content.length() - 1; i++) {
            var map = new HashMap<Character, Object>();
            curMap.put(content.charAt(i), map);
            curMap = map;
        }

        curMap.put(content.charAt(content.length() - 1), data);
        System.out.println(searchData(values, content));
    }

    static String searchData(HashMap<Character, Object> values, String word) {
        var cur = values;
        for (var i = 0; i < word.length() - 1; i++) {
            cur = (HashMap<Character, Object>) cur.get(word.charAt(i));
        }
        return (String) cur.get(word.charAt(word.length() - 1));
    }
}
