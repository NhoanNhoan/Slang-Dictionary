package delivery;

import entity.SlangWord;
import service.SlangConsoleService;

import java.io.IOException;
import java.util.Scanner;

public class SlangConsole {
    private SlangConsoleService service;
    private Scanner input;

    public SlangConsole(String path) throws IOException {
        this.service = new SlangConsoleService(path);
        this.input = new Scanner(System.in);
    }

    private void HandleSearchSlangWord() {

    }

    private void HandleSearchDefinition() {

    }

    private void HandleShowHistory() {

    }

    private void HandleInsert() throws IOException {
        System.out.println("-> Definition: ");
        String definition = this.input.nextLine();

        System.out.println("-> Value: ");
        String value = this.input.nextLine();

        var word = new SlangWord(definition, value);
        var msg = this.service.insert(word) ? "Success" : "Fail";
        System.out.println(msg);
    }

    private void HandleEdit() {

    }

    private void HandleDelete() {

    }

    private void HandleReset() {

    }

    private void HandleRandomSlangWord() {

    }

    private void HandleQuizByValue() {

    }

    private void HandleQuizByDefinition() {

    }
}
