import delivery.SlangConsole;
import service.SlangWordService;

import java.io.IOException;

public class Main {
    public static void main(String[] argv) throws IOException {
        var console = new SlangConsole("slang.txt");
        console.Run();
    }
}
