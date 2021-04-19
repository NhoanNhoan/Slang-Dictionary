package service;

import entity.SlangWord;

import java.io.IOException;
import java.util.List;

public interface SlangWordService {
    boolean exists(SlangWord word);
    boolean insert(SlangWord... words) throws IOException;
    boolean update(String definition, String value);
    boolean delete(String definition);
    List<SlangWord> fetch() throws IOException;
}
