package repo;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlangTextRepo implements SlangRepo {
    @Override
    public List<String> read(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        List<String> content = new ArrayList<>();
        String value = null;
        while ((value = reader.readLine()) != null) {
            content.add(value);
        }
        return content;
    }

    @Override
    public boolean write(String path) {
        return false;
    }
}
