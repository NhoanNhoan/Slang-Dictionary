package service;

import entity.SlangWord;
import repo.SlangRepo;
import repo.textfile.SlangTextRepo;

import java.io.IOException;
import java.util.*;

public class SlangConsoleService implements SlangWordService {
    private HashMap<String, String> dic;
    private SlangRepo repo;

    public SlangConsoleService(String path) throws IOException {
        this.repo = new SlangTextRepo(path);
        this.dic = new HashMap<>();
    }

    public String getDefinition(String slang) {
        return this.dic.get(slang);
    }

    public SlangWord random() {
        Random generator = new Random();
        Object[] values = this.dic.keySet().toArray(new String[0]);
        String randomValue = values[generator.nextInt(values.length)].toString();
        return new SlangWord(randomValue, this.dic.get(randomValue));
    }

    @Override
    public boolean exists(SlangWord word) {
        return this.dic.get(word.getDefinition()) != null;
    }

    @Override
    public boolean insert(SlangWord... words) throws IOException {
        Arrays.stream(words).forEach(w -> this.dic.put(w.getDefinition(), w.getValue()));
        return repo.Save(this.getList());
    }

    @Override
    public boolean update(String definition, String value) {
        return this.dic.get(definition) != null && this.dic.put(definition, value) != null;
    }

    @Override
    public boolean delete(String definition) {
        return this.dic.get(definition) != null && this.dic.remove(definition) != null;
    }

    @Override
    public List<SlangWord> fetch() throws IOException {
        return this.repo.Load();
    }

    private List<SlangWord> getList() {
        List<SlangWord> words = new ArrayList<>();
        this.dic.forEach((d, v) -> words.add(new SlangWord(d, v)));
        return words;
    }
}
