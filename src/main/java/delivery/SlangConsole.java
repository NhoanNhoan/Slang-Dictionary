package delivery;

import entity.SlangWord;
import service.ReverseDic;
import service.SlangConsoleService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SlangConsole {
    private SlangConsoleService service;
    private ReverseDic reverseDic;
    private List<String> historySearch;
    private Scanner input;

    public SlangConsole(String path) throws IOException {
        this.service = new SlangConsoleService(path);
        this.reverseDic = new ReverseDic();
        this.input = new Scanner(System.in);
        this.historySearch = new ArrayList<>();
    }

    private void HandleSearchSlang() {
        System.out.println("-> Slang: ");
        String slang = this.input.nextLine();
        String definition = this.service.getDefinition(slang);
        System.out.println(definition != null ? "Definition: " + definition : "Not found");
        this.historySearch.add(slang);
    }

    private void HandleSearchDefinition() {
        System.out.println("-> definition: ");
        String definition = this.input.nextLine();
        String result = this.reverseDic.search(definition);

    }

    private void HandleShowHistory() {
        System.out.println("Searching history: ");
        this.historySearch.forEach(w -> System.out.println("-> " + w));
    }

    private void HandleInsert() throws IOException {
        System.out.println("Enter:");
        System.out.print("-> Slang: ");
        String definition = this.input.nextLine();

        System.out.println("-> Definition: ");
        String value = this.input.nextLine();

        var word = new SlangWord(definition, value);
        var msg = this.service.insert(word) ? "Success" : "Fail";
        System.out.println(msg);
    }

    private void HandleEdit() {
        System.out.println("Enter:");
        System.out.print("-> slang: ");
        String slang = this.input.nextLine();
        if (this.service.exists(new SlangWord(slang, ""))) {
            System.out.println("Not found");
            return;
        }

        System.out.println("-> definition: ");
        String definition = this.input.nextLine();
        System.out.println(this.service.update(slang, definition) ? "Success" : "Fail");
    }

    private void HandleDelete() {
        System.out.println("Enter: ");
        System.out.print("-> slang: ");
        String slang = this.input.nextLine();
        if (!this.service.exists(new SlangWord(slang, ""))) {
            System.out.println("Not found");
            return;
        }

        System.out.println(this.service.delete(slang) ? "Success" : "Fail");
    }

    private void HandleReset() {

    }

    private void HandleRandomSlangWord() {
        var word = this.service.random();
        System.out.println("Random word: " + word.getDefinition() + ": " + word.getDefinition());
    }

    private void HandleQuizByValue() {

    }

    private void HandleQuizByDefinition() {

    }
}
