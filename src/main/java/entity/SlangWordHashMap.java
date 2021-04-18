package entity;

import java.util.HashMap;

public class SlangWordHashMap {
    private HashMap<String, String> slangDic;

    public SlangWordHashMap(HashMap<String, String> hashMap) {
        this.slangDic = hashMap;
    }

    public DictionaryWord get(String definition) {
        if (this.slangDic.get(definition) == null) {
            return null;
        }
        return new SlangWord(definition, this.slangDic.get(definition));
    }

    public boolean add(DictionaryWord word) {
        if (null != word) {
            this.slangDic.put(word.getDefinition(), word.getValue());
            return true;
        }
        return false;
    }

    public boolean set(DictionaryWord word) {
        if (this.slangDic.get(word.getDefinition()) != null) {
           this.add(word);
           return true;
        }
        return false;
    }
}
