package service;

import entity.SlangWord;

import java.util.HashMap;

public class ReverseDic {
    private HashMap<Character, Object> rootMap;

    public ReverseDic() {
        this.rootMap = new HashMap<>();
    }

    public void add(SlangWord word) {
        final String value = word.getDefinition();
        var curMap = this.rootMap;
        for (var i = 0; i < value.length() - 1; i++) {
            var map = new HashMap<Character, Object>();
            curMap.put(value.charAt(i), map);
            curMap = map;
        }

        char lastLetter = value.charAt(value.length() - 1);
        curMap.put(lastLetter, word.getDefinition());
    }

    public String search(String value) {
        var cur = this.rootMap;
        for (var i = 0; i < value.length() - 1; i++) {
            var map = cur.get(value.charAt(i));
            if (null == map) {
                return null;
            }

            cur = (HashMap<Character, Object>) map;
        }
        return (String) cur.get(value.charAt(value.length() - 1));
    }
}
