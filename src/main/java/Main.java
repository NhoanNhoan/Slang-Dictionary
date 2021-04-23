import service.SlangWordService;

import java.io.IOException;

public class Main {
    public static void main(String[] argv) throws IOException {
        SlangWordService service = new SlangWordService("/home/nhandba/Documents/Slang-Dictionary/src/slang.txt");
        long startTime = System.nanoTime();

        System.out.println(service.searchByDefinition("No"));
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);
        System.out.println((float)duration / 1000000 + "ms");
    }
}
