package service.interfaces;

import java.util.List;

public interface DictionarySearching {
    List<String> searchByWord(String slang);
    List<String> searchByDefinition(String definition);
}
