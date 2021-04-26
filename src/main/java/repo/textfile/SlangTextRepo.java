package repo.textfile;

import entity.SlangWord;
import repo.SlangRepo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SlangTextRepo implements SlangRepo {
    private String path;

    public SlangTextRepo(String path) throws IOException {
        this.path = path;
    }

    @Override
    public List<SlangWord> Load() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(this.path));
        List<SlangWord> content = new ArrayList<>();
        String delimiter = "`";
        String value = null;
        while ((value = reader.readLine()) != null) {
            var word = SlangWord.parse(value, delimiter);
            content.add(word);
        }

        reader.close();
        return content;
    }

    @Override
    public boolean Save(List<SlangWord> words) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.path));
        words.forEach(word -> {
            try {
                for (var definition : word.getDefinitions()) {
                    writer.write(word.getWord() + "`" + definition + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        writer.close();
        return true;
    }
}
