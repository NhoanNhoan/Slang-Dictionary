package delivery.io;


import entity.Quiz;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class ConsoleIO {
    private final Scanner scanner;

    public ConsoleIO() {
        this.scanner = new Scanner(System.in);
    }

    public void display(String msg) {
        System.out.print(msg);
    }

    public void display(String success, String fail, boolean right) {
        String msg = right ? success : fail;
        this.display(msg);
    }

    public void displaySlangDefinitions(List<String> definitions) {
        if (null == definitions || definitions.isEmpty()) {
            display("\nNo result.\n\n");
            return;
        }
        display("\n* Meaning:\n");
        definitions.forEach(d -> display("\t- " + d + "\n"));
        display("\n");
    }

    public void displayQuiz(Quiz quiz) {
        AtomicInteger i = new AtomicInteger(1);
        quiz.getSelections().forEach(e -> display(i.getAndIncrement() + ". " + e + "\n"));
    }

    public String enterString(String msg) {
        this.display(msg);
        return this.scanner.nextLine();
    }

    public Integer enterInt(String msg) {
        this.display(msg);
        return this.scanner.nextInt();
    }
}
