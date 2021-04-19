package repo;

import entity.SlangWord;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface SlangRepo {
    List<SlangWord> Load() throws IOException;
    boolean Save(List<SlangWord> words) throws IOException;
}
