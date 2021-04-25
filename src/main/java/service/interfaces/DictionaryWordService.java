package service.interfaces;

import entity.DictionaryWord;
import entity.SlangWord;

import java.io.IOException;
import java.util.List;

public interface DictionaryWordService {
    boolean exists(String word);
    boolean insert(SlangWord... words) throws IOException;
    boolean update(SlangWord... words) throws IOException;
    boolean delete(SlangWord... words) throws IOException;
    List<SlangWord> fetch() throws IOException;
    DictionaryWord randomWord();
}
