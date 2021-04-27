package delivery;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

class Menu {
    private String[] selections;

    public Menu(String ...selections) {
        this.selections = selections;
    }

    int receiveSelection() {
        showSelections();
        System.out.print("-> Your selection: ");
        return new Scanner(System.in).nextInt();
    }

    void showSelections() {
        AtomicInteger index = new AtomicInteger(1);
        Arrays.stream(this.selections).forEach(e -> System.out.println((index.getAndIncrement()) + ". " + e));
    }
}
