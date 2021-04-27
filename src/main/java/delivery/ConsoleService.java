package delivery;

import entity.SlangWord;
import service.Quiz;
import service.SlangWordQuiz;
import service.SlangWordService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class ConsoleService {
    private SlangWordService service;
    private List<String> historySearch;
    private Scanner input;

    public ConsoleService(String path) throws IOException {
        this.service = new SlangWordService(path);
        this.input = new Scanner(System.in);
        this.historySearch = new ArrayList<>();
    }

    public void HandleSearchSlang() {
        System.out.print("-> Enter slang: ");
        String word = this.input.nextLine();
        List<String> definitions = this.service.search(word);
        showList(definitions);
        this.historySearch.add(word);
    }

    private void showList(List<String> definitions) {
        if (null == definitions) {
            System.out.println("Not found");
            return;
        }

        System.out.println("Definitions: ");
        definitions.forEach(d -> System.out.println("-> " + d));
    }

    public void HandleSearchDefinition() {
        System.out.print("-> definition: ");
        String definition = this.input.nextLine();
        var result = this.service.searchByDefinition(definition);
        showList(result);
    }

    public void HandleShowHistory() {
        System.out.println("Searching history: ");
        this.historySearch.forEach(w -> System.out.println("-> " + w));
    }

    public void HandleInsert() throws IOException {
        System.out.println("Enter:");
        System.out.print("-> Slang: ");
        String slang = this.input.nextLine();

        if (this.service.exists(slang)) {
            if (HandleExists(slang)) {
                System.out.println("Success");
            } else {
                System.out.println("Fail");
            }

            return;
        }

        System.out.println("-> Definition: ");
        String definition = this.input.nextLine();

        var word = new SlangWord(slang, Collections.singletonList(definition));
        var msg = this.service.insert(word) ? "Success" : "Fail";
        this.service.addDefinitionToTrie(definition, slang);
        System.out.println(msg);
    }

    public void HandleEdit() throws IOException {
        System.out.println("Enter:");
        System.out.print("-> slang: ");
        String slang = this.input.nextLine();
        if (!this.service.exists(slang)) {
            System.out.println("Not found");
            return;
        }

        System.out.println("-> definition: ");
        String definition = this.input.nextLine();
        var listDefinition = new ArrayList<String>(){{add(definition);}};

        System.out.println(this.service.update(new SlangWord(slang, listDefinition)) ? "Success" : "Fail");
    }

    private boolean HandleExists(String slang) throws IOException {
        System.out.println("This word has been existed!");
        System.out.println("Overwrite(o) or duplicate(d) or no(n)");
        String userInput = this.input.nextLine();

        System.out.print("-> definition: ");
        String definition = this.input.nextLine();

        if (userInput.toLowerCase().equals("o")) {
            return service.update(
                    new SlangWord(slang,
                            Collections.singletonList(definition)));
        }

        if (userInput.toLowerCase().equals("d")) {
            return service.addDefinition(
                    new SlangWord(slang,
                            Collections.singletonList(definition)));
        }

        return userInput.toLowerCase().equals("n");
    }

    public void HandleDelete() throws IOException {
        System.out.println("Enter: ");
        System.out.print("-> slang: ");
        String slang = this.input.nextLine();
        if (!this.service.exists(slang)) {
            System.out.println("Not found");
            return;
        }

        System.out.println(this.service.delete(new SlangWord(slang, null)) ? "Success" : "Fail");
    }

    public void HandleReset() {
        this.historySearch.clear();
    }

    public void HandleRandomSlangWord() {
        var word = this.service.randomWord();
        System.out.println("Word: " + word.getWord());
        showList(word.getDefinitions());
    }

    public void HandleQuizBySlang() {
        SlangWordQuiz challenge = new SlangWordQuiz(4, this.service);
        var quiz = challenge.quizByWord();
        HandleQuiz(quiz);
    }

    public void HandleQuizByDefinition() {
        SlangWordQuiz challenge = new SlangWordQuiz(4, this.service);
        var quiz = challenge.quizByDefinition();
        HandleQuiz(quiz);
    }

    private void HandleQuiz(Quiz quiz) {
        System.out.println("What is '" + quiz.getQuestion() + "'?");

        for (var i = 0; i < quiz.getSelections().size(); i++) {
            System.out.println(i + 1 + ". " + quiz.getSelections().get(i));
        }

        System.out.println("Select:");

        int userSelection = this.input.nextInt();
        if (!quiz.isRightSelection(userSelection - 1)) {
            System.out.println("Wrong");
        } else {
            System.out.println("Right! Congratulate!");
        }
    }
}