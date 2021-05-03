package service;

import entity.SlangWord;
import entity.SlangWordExecution;
import repo.SlangRepo;
import repo.textfile.SlangTextRepo;
import service.ds.Trie;
import service.interfaces.DictionarySearching;
import service.interfaces.DictionaryWordService;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class SlangWordService implements DictionaryWordService, DictionarySearching {
    public static List<String> NO_DEFINITIONS;

    private HashMap<String, List<String>> dic;
    private final SlangRepo repo;
    public Trie trie;


    public SlangWordService(String path) throws IOException {
        this.repo = new SlangTextRepo(path);
        var list = fetch();
        this.dic = new HashMap<>();

        for (var word : list) {
           dic.put(word.getWord(), word.getDefinitions());
        }

        trie = new Trie(dic);
    }

    public void addDefinitionToTrie(String word, String slang) {
        trie.insertSentence(word, slang);
    }

    @Override
    public List<String> searchByWord(String slang) {
        return exists(slang) ? dic.get(slang) : NO_DEFINITIONS;
    }

    @Override
    public List<String> searchByDefinition(String definition) {
        return trie.search(definition);
    }

    @Override
    public SlangWord randomWord() {
        Random generator = new Random();
        Object[] values = this.dic.keySet().toArray(new String[0]);
        String randomValue = values[generator.nextInt(values.length)].toString();
        return new SlangWord(randomValue, this.dic.get(randomValue));
    }

    public String randomDefinition() {
        Random generator = new Random();
        var values = this.dic.values().toArray();
        int randomValueIdx = generator.nextInt(values.length);
        List<String> definitions = (List<String>) values[randomValueIdx];
        return definitions.get(0);
    }

    private boolean execute(SlangWordExecution exe, SlangWord... words) throws IOException {
        for (var word : words) {
            if (!exe.execute(word)) {
                return false;
            }
        }

        List<SlangWord> slangWords = new ArrayList<>();
        Arrays.stream(this.dic.keySet().toArray()).forEach(k -> slangWords.add(new SlangWord((String) k, this.dic.get(k))));
        this.repo.Save(slangWords);

        return true;
    }

    @Override
    public boolean exists(String word) {
        return this.dic.get(word) != null;
    }

    @Override
    public boolean insert(SlangWord... words) throws IOException {
        return execute(w -> !exists(w.getWord()) && dic.put(w.getWord(), w.getDefinitions()) == null, words);
    }

    @Override
    public boolean update(SlangWord... words) throws IOException {
        return execute(w -> exists(w.getWord()) && dic.replace(w.getWord(), w.getDefinitions()) != null, words);
    }

    @Override
    public boolean delete(SlangWord... words) throws IOException {
        return execute(w -> exists(w.getWord()) && dic.remove(w.getWord()) != null, words);
    }

    @Override
    public List<SlangWord> fetch() throws IOException {
        return this.repo.Load();
    }

    public void reset() throws IOException {
        var rootRepo = new SlangTextRepo("root.txt");
        var list = rootRepo.Load();
        this.dic = new HashMap<>();

        for (var word : list) {
            dic.put(word.getWord(), word.getDefinitions());
        }

        reloadTrie();
        this.repo.Save(getListSlangWords());
    }

    public void reloadTrie() {
        trie = new Trie(dic);
    }

    public boolean addDefinition(SlangWord word) {
        return this.dic.get(word.getWord()).addAll(word.getDefinitions());
    }

    private List<SlangWord> getListSlangWords() {
        List<SlangWord> words = new ArrayList<>();
        this.dic.forEach((d, v) -> words.add(new SlangWord(d, v)));
        return words;
    }
}
