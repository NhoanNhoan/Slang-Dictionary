package service;

import entity.SlangWord;

import java.io.IOException;
import java.util.List;

public interface DictionaryWordService {
    boolean exists(String word);
    boolean insert(SlangWord... words) throws IOException;
    boolean update(SlangWord... words);
    boolean delete(SlangWord... words);
    List<SlangWord> fetch() throws IOException;
}
