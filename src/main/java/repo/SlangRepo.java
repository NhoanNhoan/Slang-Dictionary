package repo;

import java.io.IOException;
import java.util.List;

public interface SlangRepo {
    List<String> read(String path) throws IOException;
   boolean write(String path);
}
