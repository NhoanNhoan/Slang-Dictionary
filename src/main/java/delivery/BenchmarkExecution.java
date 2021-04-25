package delivery;

import java.io.IOException;

public class BenchmarkExecution {
    public static void benchmark(Execution execution) throws IOException {
        long startTime = System.nanoTime();
        execution.execute();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println((float)duration / 1000000 + "ms");
    }
}
