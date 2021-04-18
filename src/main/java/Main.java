import repo.SlangRepo;
import repo.SlangTextRepo;

public class Main {
    public static void main(String[] argv)
            throws Exception
    {
        SlangTextRepo repo = new SlangTextRepo();

        long start = System.currentTimeMillis();

        var value = repo.read("D:/slang.txt");

        long elapsed = System.currentTimeMillis() - start;

        System.out.println("elapsed time = " + elapsed + "ms");
        System.out.println((elapsed * 1000.0) / 1000000 + " microseconds per execution");

        start = System.currentTimeMillis();

        var val = repo.readByBuffer("D:/slang.txt");
        System.out.println(val);

        elapsed = System.currentTimeMillis() - start;

        System.out.println("elapsed time = " + elapsed + "ms");
        System.out.println((elapsed * 1000.0) / 1000000 + " microseconds per execution");
    }
}
