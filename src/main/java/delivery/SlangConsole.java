package delivery;

import entity.SlangWord;
import service.SlangWordService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SlangConsole {
    private SlangWordService service;
    private List<String> historySearch;
    private Scanner input;

    public SlangConsole(String path) throws IOException {
        this.service = new SlangWordService(path);
        this.input = new Scanner(System.in);
        this.historySearch = new ArrayList<>();
    }

    private void HandleSearchSlang() {
        System.out.println("-> Slang: ");
        String word = this.input.nextLine();
        List<String> definitions = this.service.search(word);
        System.out.println(definitions != SlangWordService.NO_DEFINITIONS ?
                "Definitions: " + definitions : "Not found");
        this.historySearch.add(word);
    }

    private void HandleSearchDefinition() {
        System.out.println("-> definition: ");
        String definition = this.input.nextLine();
        var result = this.service.searchByDefinition(definition);
        System.out.println((null == result) ? "No Result" : result);
    }

    private void HandleShowHistory() {
        System.out.println("Searching history: ");
        this.historySearch.forEach(w -> System.out.println("-> " + w));
    }

    private void HandleInsert() throws IOException {
        System.out.println("Enter:");
        System.out.print("-> Word: ");
        String definition = this.input.nextLine();

        System.out.println("-> Definition: ");
        String value = this.input.nextLine();

        var word = new SlangWord(definition, Collections.singletonList(value));
        var msg = this.service.insert(word) ? "Success" : "Fail";
        System.out.println(msg);
    }

    private void HandleEdit() {
        System.out.println("Enter:");
        System.out.print("-> slang: ");
        String slang = this.input.nextLine();
        if (this.service.exists(slang)) {
            System.out.println("Not found");
            return;
        }

        System.out.println("-> definition: ");
        String definition = this.input.nextLine();
        var listDefinition = new ArrayList<String>(){{add(definition);}};

        System.out.println(this.service.update(new SlangWord(slang, listDefinition)) ? "Success" : "Fail");
    }

    private void HandleDelete() {
        System.out.println("Enter: ");
        System.out.print("-> slang: ");
        String slang = this.input.nextLine();
        if (!this.service.exists(slang)) {
            System.out.println("Not found");
            return;
        }

        System.out.println(this.service.delete(new SlangWord(slang, null)) ? "Success" : "Fail");
    }

    private void HandleReset() {
        this.historySearch.clear();
    }

    private void HandleRandomSlangWord() {
        var word = this.service.random();
        System.out.println("Random word: " + word.getWord() + ": " + word.getDefinitions());
    }

    private void HandleQuizByValue() {

    }

    private void HandleQuizByDefinition() {

    }
}
